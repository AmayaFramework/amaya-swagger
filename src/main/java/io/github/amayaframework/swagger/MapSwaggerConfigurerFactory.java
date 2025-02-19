package io.github.amayaframework.swagger;

import io.github.amayaframework.openui.OpenUIFactory;

import java.net.URI;
import java.util.Objects;

final class MapSwaggerConfigurerFactory implements SwaggerConfigurerFactory {
    private static final MimeTyper TYPER = new MapMimeTyper();

    @Override
    public SwaggerConfigurer create(OpenUIFactory factory, URI root) {
        Objects.requireNonNull(factory);
        Objects.requireNonNull(root);
        if (root.isAbsolute()) {
            throw new IllegalArgumentException("Static root must be relative path");
        }
        return new MapSwaggerConfigurer(factory, TYPER, root);
    }
}
