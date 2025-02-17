package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Function0;
import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.swaggerui.SwaggerUIFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

public class Main {

    public static URI getURI(URI root, String swagger) {
        var uri = URI.create(swagger).normalize();
        if (uri.isAbsolute()) {
            return uri;
        }
        return URI.create(root.getPath() + "/" + uri.getPath());
    }

    public static void main(String[] args) throws Throwable {
        var app = WebBuilders
                .create()
                .setServerFactory(new JettyServerFactory())
                .build();
        var uif = new SwaggerUIFactory();
        var cfg = new MapSwaggerConfigurer(uif, new MapMimeTyper(), URI.create("/swagger"));
        cfg.addDocument(new OpenAPIDocument() {
            @Override
            public String getTitle() {
                return null;
            }

            @Override
            public URI getPath() {
                return URI.create("https://petstore.swagger.io/v2/swagger.json");
            }

            @Override
            public Function0<InputStream> getProvider() {
                return null;
            }
        });
        cfg.addDocument(new OpenAPIDocument() {
            @Override
            public String getTitle() {
                return null;
            }

            @Override
            public URI getPath() {
                return URI.create("swagger.json");
            }

            @Override
            public Function0<InputStream> getProvider() {
                return () -> new FileInputStream("swagger.json");
            }
        });
        app.addHandler(cfg.createHandler());
        app.bind(8080);
        app.run();
    }
}
