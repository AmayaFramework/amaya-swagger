package io.github.amayaframework.swaggerui;

import io.github.amayaframework.openui.OpenUI;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Set;

final class SwaggerUI implements OpenUI {
    private static final Set<String> PARTS = Set.of(
            "favicon-16x16.png",
            "favicon-32x32.png",
            "index.css",
            "index.html",
            "oauth2-redirect.html",
            "swagger-ui.css",
            "swagger-ui-bundle.js",
            "swagger-ui-standalone-preset.js"
    );

    private final URI root;
    private final byte[] indexBuffer;

    SwaggerUI(URI root, byte[] indexBuffer) {
        this.root = root;
        this.indexBuffer = indexBuffer;
    }

    @Override
    public String getIndex() {
        return Util.INDEX;
    }

    @Override
    public URI getRoot() {
        return root;
    }

    @Override
    public Iterable<String> getParts() {
        return PARTS;
    }

    @Override
    public InputStream getInputStream(String part) {
        if (Util.INDEX.equals(part)) {
            return new ByteArrayInputStream(indexBuffer);
        }
        if (!PARTS.contains(part)) {
            return null;
        }
        return Util.getInputStream(part);
    }
}
