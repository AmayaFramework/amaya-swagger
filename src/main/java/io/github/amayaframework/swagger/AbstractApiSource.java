package io.github.amayaframework.swagger;

import java.net.URI;

/**
 * Base implementation of {@link OpenApiSource} providing common
 * storage for {@link #uri()} and {@link #name()}.
 * <p>
 * Subclasses must supply the format, charset, and input stream provider.
 */
public abstract class AbstractApiSource implements OpenApiSource {
    protected final URI uri;
    protected final String name;

    /**
     * Creates a new API source with the given metadata.
     *
     * @param uri  the URI identifying the OpenAPI document
     * @param name a human-readable name for this source
     */
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
