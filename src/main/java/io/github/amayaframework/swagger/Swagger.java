package io.github.amayaframework.swagger;

import io.github.amayaframework.environment.Environment;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Entry point for creating Swagger configuration and tasks.
 * <p>
 * Provides static factory methods for building {@link SwaggerTaskBuilder}
 * and {@link SwaggerApplicationConfigurer} instances in different styles.
 * <p>
 * Typical usage:
 * <pre>{@code
 * // Build a task manually
 * var task = Swagger.taskBuilder()
 *     .uiFactory(new SwaggerUiFactory())
 *     .exposeDocument(Sources.of("/openapi.yaml", "My API"))
 *     .build();
 *
 * // Register a configurer with programmatic configuration
 * app.configurer().add(Swagger.configurer((cfg, env) -> cfg
 *     .uiFactory(new SwaggerUiFactory())
 *     .root(URI.create("/swagger"))
 *     .exposeDocument(Sources.of("/openapi.yaml", "My API"))
 * ));
 *
 * // Register a configurer that reads from options
 * app.configurer().add(Swagger.configurer());
 * }</pre>
 */
public final class Swagger {
    private Swagger() {
    }

    /**
     * Creates a new {@link SwaggerTaskBuilder} with the given root.
     *
     * @param defaultRoot the default root path under which Swagger is served
     * @return a new task builder
     */
    public static SwaggerTaskBuilder taskBuilder(String defaultRoot) {
        return new SwaggerTaskBuilder(defaultRoot);
    }

    /**
     * Creates a new {@link SwaggerTaskBuilder} with the default root
     * ({@code /swagger}).
     *
     * @return a new task builder
     */
    public static SwaggerTaskBuilder taskBuilder() {
        return new SwaggerTaskBuilder(SwaggerApplicationConfigurer.DEFAULT_ROOT);
    }

    /**
     * Creates a {@link SwaggerApplicationConfigurer} with a custom root and
     * a configuration action that has access to {@link SwaggerConfigurer}
     * and the application {@link Environment}.
     *
     * @param defaultRoot the default root path for Swagger
     * @param action      configuration action
     * @return a new application configurer
     */
    public static SwaggerApplicationConfigurer configurer(String defaultRoot, BiConsumer<SwaggerConfigurer, Environment> action) {
        return new SwaggerApplicationConfigurer(defaultRoot, action);
    }

    /**
     * Creates a {@link SwaggerApplicationConfigurer} with the default root
     * and a configuration action that has access to {@link SwaggerConfigurer}
     * and the application {@link Environment}.
     *
     * @param action configuration action
     * @return a new application configurer
     */
    public static SwaggerApplicationConfigurer configurer(BiConsumer<SwaggerConfigurer, Environment> action) {
        return new SwaggerApplicationConfigurer(SwaggerApplicationConfigurer.DEFAULT_ROOT, action);
    }

    /**
     * Creates a {@link SwaggerApplicationConfigurer} with a custom root and
     * a configuration action that directly configures the {@link SwaggerTaskBuilder}.
     *
     * @param defaultRoot the default root path for Swagger
     * @param action      configuration action
     * @return a new application configurer
     */
    public static SwaggerApplicationConfigurer configurer(String defaultRoot, Consumer<SwaggerConfigurer> action) {
        var ret = new SwaggerApplicationConfigurer(defaultRoot, false);
        action.accept(ret.getBuilder());
        return ret;
    }

    /**
     * Creates a {@link SwaggerApplicationConfigurer} with the default root
     * and a configuration action that directly configures the {@link SwaggerTaskBuilder}.
     *
     * @param action configuration action
     * @return a new application configurer
     */
    public static SwaggerApplicationConfigurer configurer(Consumer<SwaggerConfigurer> action) {
        return configurer(SwaggerApplicationConfigurer.DEFAULT_ROOT, action);
    }

    /**
     * Creates a {@link SwaggerApplicationConfigurer} that can be configured
     * either programmatically or via options.
     *
     * @param configure if {@code true}, reads configuration from
     *                  {@link io.github.amayaframework.options.OptionSet}
     *                  (group {@link SwaggerOptions#SWAGGER_GROUP});
     *                  if {@code false}, uses programmatic configuration.
     * @return a new application configurer
     */
    public static SwaggerApplicationConfigurer configurer(boolean configure) {
        return new SwaggerApplicationConfigurer(configure);
    }

    /**
     * Creates a {@link SwaggerApplicationConfigurer} that does not
     * read configuration from options. Only programmatic configuration
     * (if provided) will be applied.
     *
     * @return a new application configurer
     */
    public static SwaggerApplicationConfigurer configurer() {
        return new SwaggerApplicationConfigurer(false);
    }
}
