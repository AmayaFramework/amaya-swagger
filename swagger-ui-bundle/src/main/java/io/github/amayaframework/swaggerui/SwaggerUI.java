package io.github.amayaframework.swaggerui;

import io.github.amayaframework.openui.OpenUI;
import io.github.amayaframework.openui.Part;

import java.net.URI;
import java.util.Map;

final class SwaggerUI implements OpenUI {
    private final URI root;
    private final Map<String, Part> parts;
    private final Part index;

    SwaggerUI(URI root, Map<String, Part> parts, Part index) {
        this.root = root;
        this.parts = parts;
        this.index = index;
    }

    @Override
    public URI root() {
        return root;
    }

    @Override
    public Part index() {
        return index;
    }

    @Override
    public Part part(String name) {
        return parts.get(name);
    }

    @Override
    public Iterable<Part> parts() {
        return parts.values();
    }
}
