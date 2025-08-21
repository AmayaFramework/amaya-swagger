package io.github.amayaframework.swaggerui;

import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.openui.OpenApiEntry;
import io.github.amayaframework.openui.OpenUI;
import io.github.amayaframework.openui.OpenUIFactory;
import io.github.amayaframework.openui.Part;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class SwaggerUIFactory implements OpenUIFactory {
    private static final String UTF_8 = "utf-8";
    private static final String INIT = "init.js";
    private static final Part INDEX = new ResourcePart("index.html", MimeType.HTML, UTF_8);
    private static final Map<String, Part> PARTS = Map.of(
            "favicon-16x16.png", new ResourcePart("favicon-16x16.png", MimeType.PNG),
            "favicon-32x32.png", new ResourcePart("favicon-32x32.png", MimeType.PNG),
            "index.html", INDEX,
            "index.css", new ResourcePart("index.css", MimeType.CSS, UTF_8),
            "oauth2-redirect.html", new ResourcePart("oauth2-redirect.html", MimeType.HTML, UTF_8),
            "swagger-ui.css", new ResourcePart("swagger-ui.css", MimeType.CSS, UTF_8),
            "swagger-ui-bundle.js", new ResourcePart("swagger-ui-bundle.js", MimeType.APPLICATION_JAVASCRIPT, UTF_8),
            "swagger-ui-standalone-preset.js", new ResourcePart("swagger-ui-standalone-preset.js", MimeType.APPLICATION_JAVASCRIPT, UTF_8)
    );

    private static BufferPart createBufferPart(String buffer) {
        return new BufferPart(INIT, MimeType.APPLICATION_JAVASCRIPT, buffer.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public OpenUI create(URI root, URI uri) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(uri);
        var parts = new HashMap<>(PARTS);
        parts.put(INIT, createBufferPart(StringUtil.generateInit(uri)));
        return new SwaggerUI(root, parts, INDEX);
    }

    @Override
    public OpenUI create(URI root, OpenApiEntry entry) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(entry);
        var parts = new HashMap<>(PARTS);
        parts.put(INIT, createBufferPart(StringUtil.generateInit(entry)));
        return new SwaggerUI(root, parts, INDEX);
    }

    @Override
    public OpenUI create(URI root, OpenApiEntry... entries) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(entries);
        var parts = new HashMap<>(PARTS);
        parts.put(INIT, createBufferPart(StringUtil.generateInit(entries)));
        return new SwaggerUI(root, parts, INDEX);
    }

    @Override
    public OpenUI create(URI root, Iterable<OpenApiEntry> entries) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(entries);
        var parts = new HashMap<>(PARTS);
        parts.put(INIT, createBufferPart(StringUtil.generateInit(entries)));
        return new SwaggerUI(root, parts, INDEX);
    }
}
