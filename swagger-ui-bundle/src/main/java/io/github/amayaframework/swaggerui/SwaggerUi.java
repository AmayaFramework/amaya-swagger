package io.github.amayaframework.swaggerui;

import io.github.amayaframework.openui.OpenUi;
import io.github.amayaframework.openui.Part;

import java.util.Map;

final class SwaggerUi implements OpenUi {
    private final Map<String, Part> parts;
    private final Part index;

    SwaggerUi(Map<String, Part> parts, Part index) {
        this.parts = parts;
        this.index = index;
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
