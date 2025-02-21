package io.github.amayaframework.swagger;

import io.github.amayaframework.openui.OpenUIFactory;

import java.net.URI;

/**
 * A utility class for creating instances of {@link SwaggerConfigurer}.
 */
public final class SwaggerConfigurers {
    public static final SwaggerConfigurerFactory SWAGGER_CONFIGURER_FACTORY = new MapSwaggerConfigurerFactory();
    public static final URI DEFAULT_ROOT = URI.create("/swagger");

    private SwaggerConfigurers() {
    }

    /**
     * Creates a {@link SwaggerConfigurer} with given {@link OpenUIFactory} and default static root.
     *
     * @param factory the specified {@link OpenUIFactory} instance, must be non-null
     * @param root    the specified default static root, must be non-null
     * @return the {@link SwaggerConfigurer} instance
     */
    public static SwaggerConfigurer create(OpenUIFactory factory, URI root) {
        return SWAGGER_CONFIGURER_FACTORY.create(factory, root);
    }

    /**
     * Creates a {@link SwaggerConfigurer} with given {@link OpenUIFactory}.
     * Uses the {@link SwaggerConfigurers#DEFAULT_ROOT} as default static root.
     *
     * @param factory the specified {@link OpenUIFactory} instance, must be non-null
     * @return the {@link SwaggerConfigurer} instance
     */
    public static SwaggerConfigurer create(OpenUIFactory factory) {
        return SWAGGER_CONFIGURER_FACTORY.create(factory, DEFAULT_ROOT);
    }
}
