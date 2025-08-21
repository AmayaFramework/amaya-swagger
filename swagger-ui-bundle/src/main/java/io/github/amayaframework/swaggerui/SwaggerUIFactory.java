package io.github.amayaframework.swaggerui;

import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.openui.OpenApiEntry;
import io.github.amayaframework.openui.OpenUI;
import io.github.amayaframework.openui.OpenUIFactory;
import io.github.amayaframework.openui.Part;

import java.net.URI;
import java.util.Map;

public final class SwaggerUIFactory implements OpenUIFactory {
    private static final Map<String, Part> PARTS = Map.of(
            "favicon-16x16.png", new ResourcePart("favicon-16x16.png", MimeType.PNG),
            "favicon-32x32.png", new ResourcePart("favicon-32x32.png", MimeType.PNG),
            "index.css", new ResourcePart("index.css", MimeType.CSS),
            "oauth2-redirect.html", new ResourcePart("oauth2-redirect.html", MimeType.HTML),
            "swagger-ui.css", new ResourcePart("swagger-ui.css", MimeType.CSS),
            "swagger-ui-bundle.js", new ResourcePart("swagger-ui-bundle.js", MimeType.APPLICATION_JAVASCRIPT),
            "swagger-ui-standalone-preset.js", new ResourcePart("swagger-ui-standalone-preset.js", MimeType.APPLICATION_JAVASCRIPT)
    );

    @Override
    public OpenUI create(URI root, URI uri) {
        // TODO
        return null;
    }

    @Override
    public OpenUI create(URI root, OpenApiEntry entry) {
        // TODO
        return null;
    }

    @Override
    public OpenUI create(URI root, OpenApiEntry... entries) {
        // TODO
        return null;
    }

    @Override
    public OpenUI create(URI root, Iterable<OpenApiEntry> entries) {
        // TODO
        return null;
    }
}
