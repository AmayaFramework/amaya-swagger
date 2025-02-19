package io.github.amayaframework.swagger;

import java.net.URI;

/**
 *
 */
public abstract class AbstractOpenAPIDocument implements OpenAPIDocument {
    protected final URI path;
    protected final String title;

    /**
     *
     * @param path
     * @param title
     */
    protected AbstractOpenAPIDocument(URI path, String title) {
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
