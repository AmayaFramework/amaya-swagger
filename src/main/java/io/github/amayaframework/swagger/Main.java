package io.github.amayaframework.swagger;

import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.MimeData;
import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.github.amayaframework.openui.ApiEntry;
import io.github.amayaframework.swaggerui.SwaggerUIFactory;

import java.io.FileInputStream;
import java.net.URI;

public class Main {
    private static final MimeType YAML = new MimeType("text", "yaml", true);

    public static URI getURI(URI root, String swagger) {
        var uri = URI.create(swagger).normalize();
        if (uri.isAbsolute()) {
            return uri;
        }
        return URI.create(root.getPath() + "/" + uri.getPath());
    }

    public static void main(String[] args) throws Throwable {
        var builder = WebBuilders.create();
        var app = builder
                .setServerFactory(new JettyServerFactory())
                .build();
        app.bind(8080);
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
        app.run(ctx -> {
            var rq = ctx.getRequest();
            var rp = ctx.getResponse();
            System.out.println(rq.getPath());
            var uri = rq.getRequestURI();
            var comp = root.relativize(uri);
            var path = comp.getPath();
            if (path.equals("/")) {
                path = ui.getIndex();
            }
            if (path.equals("swagger.json")) {
                rp.setMimeType(MimeType.JSON);
                var is = new FileInputStream("swagger.json");
                is.transferTo(rp.getOutputStream());
                is.close();
                return;
            }
            if (path.equals("swagger.yaml")) {
                rp.setMimeData(MimeData.of(YAML, "charset", "utf-8"));
                var is = new FileInputStream("swagger.yaml");
                is.transferTo(rp.getOutputStream());
                is.close();
                return;
            }
            var found = ui.getInputStream(path);
            if (found == null) {
                rp.sendError(HttpCode.NOT_FOUND, "File " + path + " not found");
                return;
            }
            found.transferTo(rp.getOutputStream());
            found.close();
        });
    }
}
