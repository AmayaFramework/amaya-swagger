package io.github.amayaframework.swagger;

import java.net.URI;

public abstract class AbstractOpenAPIDocument implements OpenAPIDocument {
    protected final String title;
    protected final URI path;

    protected AbstractOpenAPIDocument(String title, URI path) {
        this.title = title;
        this.path = path;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public URI getPath() {
        return path;
    }
}
