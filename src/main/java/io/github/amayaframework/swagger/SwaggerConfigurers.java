package io.github.amayaframework.swagger;

import io.github.amayaframework.openui.OpenUIFactory;

import java.net.URI;

public final class SwaggerConfigurers {
    public static SwaggerConfigurerFactory SWAGGER_CONFIGURER_FACTORY = new MapSwaggerConfigurerFactory();
    public static URI DEFAULT_ROOT = URI.create("/swagger");

    private SwaggerConfigurers() {
    }

    public static SwaggerConfigurer create(OpenUIFactory factory, URI root) {
        return SWAGGER_CONFIGURER_FACTORY.create(factory, root);
    }

    public static SwaggerConfigurer create(OpenUIFactory factory) {
        return SWAGGER_CONFIGURER_FACTORY.create(factory, DEFAULT_ROOT);
    }
}
