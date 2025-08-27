package io.github.amayaframework.swagger;

import java.net.URI;

public abstract class AbstractApiSource implements OpenApiSource {
    protected final URI uri;
    protected final String name;

    protected AbstractApiSource(URI uri, String name) {
        this.name = name;
        this.uri = uri;
    }

    @Override
    public URI uri() {
        return uri;
    }

    @Override
    public String name() {
        return name;
    }
}
