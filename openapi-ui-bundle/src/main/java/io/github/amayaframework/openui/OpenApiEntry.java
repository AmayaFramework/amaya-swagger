package io.github.amayaframework.openui;

import java.net.URI;
import java.util.Objects;

/**
 * Represents a single OpenAPI specification entry that can be
 * referenced by {@link OpenUi}. Each entry consists of a unique
 * display name and the URI of the specification.
 */
public final class OpenApiEntry {
    private final URI uri;
    private final String name;

    /**
     * Creates a new entry for the given specification.
     *
     * @param uri  the location of the OpenAPI definition
     * @param name the display name of the entry
     */
    public OpenApiEntry(URI uri, String name) {
        this.uri = Objects.requireNonNull(uri);
        this.name = Objects.requireNonNull(name);
    }

    /**
     * Factory method for creating an entry.
     *
     * @param uri  the location of the OpenAPI definition
     * @param name the display name of the entry
     * @return a new {@link OpenApiEntry} instance
     */
    public static OpenApiEntry of(URI uri, String name) {
        return new OpenApiEntry(uri, name);
    }

    /**
     * Returns the display name of this entry.
     *
     * @return the entry name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the URI of the OpenAPI definition.
     *
     * @return the specification URI
     */
    public URI getURI() {
        return uri;
    }

    @Override
    public String toString() {
        return uri + ":" + name;
    }
}
