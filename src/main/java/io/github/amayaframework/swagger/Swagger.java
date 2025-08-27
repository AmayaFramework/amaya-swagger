package io.github.amayaframework.swagger;

import io.github.amayaframework.environment.Environment;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * TODO
 */
public final class Swagger {
    private Swagger() {
    }

    /**
     * TODO
     * @param defaultRoot
     * @return
     */
    public static SwaggerTaskBuilder taskBuilder(String defaultRoot) {
        return new SwaggerTaskBuilder(defaultRoot);
    }

    /**
     * TODO
     * @return
     */
    public static SwaggerTaskBuilder taskBuilder() {
        return new SwaggerTaskBuilder(SwaggerApplicationConfigurer.DEFAULT_ROOT);
    }

    /**
     * TODO
     * @param defaultRoot
     * @param action
     * @return
     */
    public static SwaggerApplicationConfigurer applicationConfigurer(String defaultRoot, BiConsumer<SwaggerConfigurer, Environment> action) {
        return new SwaggerApplicationConfigurer(defaultRoot, action);
    }

    /**
     * TODO
     * @param action
     * @return
     */
    public static SwaggerApplicationConfigurer applicationConfigurer(BiConsumer<SwaggerConfigurer, Environment> action) {
        return new SwaggerApplicationConfigurer(SwaggerApplicationConfigurer.DEFAULT_ROOT, action);
    }

    /**
     * TODO
     * @param defaultRoot
     * @param action
     * @return
     */
    public static SwaggerApplicationConfigurer applicationConfigurer(String defaultRoot, Consumer<SwaggerConfigurer> action) {
        var ret = new SwaggerApplicationConfigurer(defaultRoot, false);
        action.accept(ret.getBuilder());
        return ret;
    }

    /**
     * TODO
     * @param action
     * @return
     */
    public static SwaggerApplicationConfigurer applicationConfigurer(Consumer<SwaggerConfigurer> action) {
        return applicationConfigurer(SwaggerApplicationConfigurer.DEFAULT_ROOT, action);
    }

    /**
     * TODO
     * @return
     */
    public static SwaggerApplicationConfigurer applicationConfigurer() {
        return new SwaggerApplicationConfigurer(false);
    }
}
