package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Runnable1;
import io.github.amayaframework.environment.Environment;
import io.github.amayaframework.options.Key;
import io.github.amayaframework.options.OptionSet;
import io.github.amayaframework.web.WebApplication;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Application-level configurer for integrating Swagger into a {@link WebApplication}.
 * <p>
 * This class wires together {@link SwaggerTaskBuilder} with either:
 * <ul>
 *   <li>Options provided via {@link OptionSet} (when {@code configure = true}), or</li>
 *   <li>A custom configuration action supplied as a {@link BiConsumer}.</li>
 * </ul>
 * <p>
 * Typical usage:
 * <pre>{@code
 * // Use options-based configuration
 * app.configurer().add(new SwaggerApplicationConfigurer(true));
 *
 * // Or programmatic configuration
 * app.configurer().add(new SwaggerApplicationConfigurer(
 *     (config, env) -> config
 *         .uiFactory(new SwaggerUiFactory())
 *         .root(URI.create("/swagger"))
 *         .exposeDocument(Sources.of("/openapi.yaml", "My API"))
 * ));
 * }</pre>
 */
public final class SwaggerApplicationConfigurer implements Runnable1<WebApplication> {
    /**
     * Default root path for Swagger UI.
     */
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
     * Creates a configurer that uses options-based configuration.
     *
     * @param defaultRoot the default root path for Swagger UI
     * @param configure   if {@code true}, reads settings from {@link OptionSet}
     */
    public SwaggerApplicationConfigurer(String defaultRoot, boolean configure) {
        this.builder = new SwaggerTaskBuilder(defaultRoot);
        this.action = null;
        this.configure = configure;
    }

    /**
     * Creates a configurer with a custom default root.
     *
     * @param defaultRoot the default root path for Swagger UI
     */
    public SwaggerApplicationConfigurer(String defaultRoot) {
        this(defaultRoot, false);
    }

    /**
     * Creates a configurer that optionally uses options-based configuration.
     *
     * @param configure if {@code true}, reads settings from {@link OptionSet}
     */
    public SwaggerApplicationConfigurer(boolean configure) {
        this(DEFAULT_ROOT, configure);
    }

    /**
     * Creates a configurer with the default root ({@code /swagger}).
     * <p>
     * Does not use options-based configuration.
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
     * Returns the underlying {@link SwaggerTaskBuilder}.
     *
     * @return the task builder
     */
    public SwaggerTaskBuilder getBuilder() {
        return builder;
    }

    /**
     * Applies configuration from the given {@link OptionSet}.
     *
     * @param options the options to apply
     */
    public void configure(OptionSet options) {
        configure(options, SwaggerOptions.UI_FACTORY, builder::uiFactory);
        configure(options, SwaggerOptions.ROOT, builder::root);
        configure(options, SwaggerOptions.DOCS, builder::addDocuments);
        configure(options, SwaggerOptions.EXPOSED_DOCS, builder::exposeDocuments);
        var negotiator = options.get(SwaggerOptions.COMPRESS_NEGOTIATOR);
        if (negotiator != null) {
            builder.negotiator(negotiator);
            return;
        }
        var configurer = options.get(SwaggerOptions.COMPRESS_NEGOTIATOR_CONFIGURER);
        if (configurer != null) {
            builder.negotiator(configurer);
        }
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
