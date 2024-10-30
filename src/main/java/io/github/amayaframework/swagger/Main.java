package io.github.amayaframework.swagger;

import io.github.amayaframework.core.WebBuilders;
import io.github.amayaframework.jetty.JettyServerFactory;
import io.swagger.v3.oas.models.OpenAPI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
    static final String URL_ENTRY_TEMPLATE = "{url:\"%url\",name:\"%name\"}";

    public static void main(String[] args) throws Throwable {
        var builder = WebBuilders.create();
        var app = builder
                .setServerFactory(new JettyServerFactory())
                .build();
        app.bind(8080);
        app.run(ctx -> {
            var rq = ctx.getRequest();
            var os = ctx.getResponse().getOutputStream();
            var seg = rq.getPathSegments().get(0);
            if (seg.equals("/")) {
                seg = "index.html";
            }
            writeToRsp(seg, os);
        });
    }

    static void writeToRsp(String name, OutputStream os) throws IOException {
        System.out.println("GETTING " + name);
        var rsc = Main.class.getResourceAsStream(name);
        if (rsc == null) {
            os.write("NOT FOUND!\n".getBytes());
            return;
        }
        rsc.transferTo(os);
        rsc.close();
    }

    interface ApiSerializer {

        void serialize(OpenAPI api, OutputStream ous);
    }

    interface ApiDeserializer {

        OpenAPI deserialize(InputStream is);
    }
}
