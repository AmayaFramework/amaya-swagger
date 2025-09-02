package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.Key;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.web.WebApplication;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * TODO
 */
public final class SwaggerApplicationConfigurer implements Runnable1<WebApplication> {
    public static final String DEFAULT_ROOT = "/swagger";
    private final SwaggerTaskBuilder builder;
    private final BiConsumer<SwaggerConfigurer, Environment> action;
    private final boolean configure;

    SwaggerApplicationConfigurer(String defaultRoot, BiConsumer<SwaggerConfigurer, Environment> action) {
        this.builder = new SwaggerTaskBuilder(defaultRoot);
        this.action = action;
        this.configure = false;
    }

    /**
     * TODO
     *
     * @param defaultRoot
     * @param configure
     */
    public SwaggerApplicationConfigurer(String defaultRoot, boolean configure) {
        this.builder = new SwaggerTaskBuilder(defaultRoot);
        this.action = null;
        this.configure = configure;
    }

    /**
     * TODO
     *
     * @param defaultRoot
     */
    public SwaggerApplicationConfigurer(String defaultRoot) {
        this(defaultRoot, false);
    }

    /**
     * TODO
     *
     * @param configure
     */
    public SwaggerApplicationConfigurer(boolean configure) {
        this(DEFAULT_ROOT, configure);
    }

    /**
     * TODO
     */
    public SwaggerApplicationConfigurer() {
        this(DEFAULT_ROOT, false);
    }

    private static <T> void configure(OptionSet set, Key<T> key, Consumer<T> cons) {
        var value = set.get(key);
        if (value != null) {
            cons.accept(value);
        }
    }

    /**
     * TODO
     *
     * @return
     */
    public SwaggerTaskBuilder getBuilder() {
        return builder;
    }

    /**
     * TODO
     *
     * @param options
     */
    public void configure(OptionSet options) {
        configure(options, SwaggerOptions.UI_FACTORY, builder::uiFactory);
        configure(options, SwaggerOptions.ROOT, builder::root);
        configure(options, SwaggerOptions.COMPRESS_NEGOTIATOR, builder::negotiator);
        configure(options, SwaggerOptions.DOCS, builder::addDocuments);
        configure(options, SwaggerOptions.EXPOSED_DOCS, builder::exposeDocuments);
    }

    @Override
    public void run(WebApplication app) {
        if (configure) {
            var options = app.options().getGroup(SwaggerOptions.SWAGGER_GROUP);
            if (options != null) {
                configure(options);
            }
        } else if (action != null) {
            action.accept(builder, app.environment());
        }
        app.configurer().add(builder.build());
    }
}
