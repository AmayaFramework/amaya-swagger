package io.github.amayaframework.swagger;

import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.swaggerui.SwaggerUIFactory;

import java.net.URL;

public class Main {

    public static void main(String[] args) throws Throwable {
        var app = WebBuilders
                .create()
                .setServerFactory(new JettyServerFactory())
                .build();
        var cfg = SwaggerConfigurers.create(new SwaggerUIFactory());
        cfg.addDocument(Documents.of(new URL("https://petstore.swagger.io/v2/swagger.json")));
        cfg.addDocument(Documents.of(Main.class, "swagger.json", "Tajik"));
        app.addHandler(cfg.createHandler());
        app.bind(8080);
        app.run();
    }
}
