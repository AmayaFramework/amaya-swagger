package io.github.amayaframework.swagger;

import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.openui.ApiEntry;
import io.github.amayaframework.swaggerui.SwaggerUIFactory;

import java.io.FileInputStream;
import java.net.URI;
import java.util.Map;

public class Main {

    public static URI getURI(URI root, String swagger) {
        var uri = URI.create(swagger).normalize();
        if (uri.isAbsolute()) {
            return uri;
        }
        return URI.create(root.getPath() + "/" + uri.getPath());
    }

    public static void main(String[] args) throws Throwable {
        // Prepare swagger
        var root = URI.create("/static/root");
        var swagger1 = getURI(root, "./swagger.json");
        var swagger2 = getURI(root, "https://petstore.swagger.io/v2/swagger.json");
        var uif = new SwaggerUIFactory();
        var ui = uif.create(
                root,
                ApiEntry.of(swagger1, "Petstore1"),
                ApiEntry.of(swagger2, "Petstore2")
        );
        var app = WebBuilders
                .create()
                .setServerFactory(new JettyServerFactory())
                .build();
        var handler = new SwaggerHandler(
                new MapMimeTyper(),
                "/static/root",
                "/ui",
                ui,
                Map.of(
                        "swagger.json",
                        () -> new FileInputStream("swagger.json")
                )
        );
        app.addHandler(handler);
        app.bind(8080);
        app.run();
    }
}
