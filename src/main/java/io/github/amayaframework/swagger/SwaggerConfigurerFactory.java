package io.github.amayaframework.swagger;

import io.github.amayaframework.openui.OpenUIFactory;

import java.net.URI;

public interface SwaggerConfigurerFactory {

    SwaggerConfigurer create(OpenUIFactory factory, URI root);
}
