package io.github.amayaframework.swagger;

import io.github.amayaframework.environment.Environment;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Swagger {
    private Swagger() {
    }

    public static SwaggerTaskBuilder taskBuilder(String defaultRoot) {
        return new SwaggerTaskBuilder(defaultRoot);
    }

    public static SwaggerTaskBuilder taskBuilder() {
        return new SwaggerTaskBuilder(SwaggerApplicationConfigurer.DEFAULT_ROOT);
    }

    public static SwaggerApplicationConfigurer applicationConfigurer(String defaultRoot, BiConsumer<SwaggerConfigurer, Environment> action) {
        return new SwaggerApplicationConfigurer(defaultRoot, action);
    }

    public static SwaggerApplicationConfigurer applicationConfigurer(BiConsumer<SwaggerConfigurer, Environment> action) {
        return new SwaggerApplicationConfigurer(SwaggerApplicationConfigurer.DEFAULT_ROOT, action);
    }

    public static SwaggerApplicationConfigurer applicationConfigurer(String defaultRoot, Consumer<SwaggerConfigurer> action) {
        var ret = new SwaggerApplicationConfigurer(defaultRoot, false);
        action.accept(ret.getBuilder());
        return ret;
    }

    public static SwaggerApplicationConfigurer applicationConfigurer(Consumer<SwaggerConfigurer> action) {
        return applicationConfigurer(SwaggerApplicationConfigurer.DEFAULT_ROOT, action);
    }

    public static SwaggerApplicationConfigurer applicationConfigurer() {
        return new SwaggerApplicationConfigurer(false);
    }
}
