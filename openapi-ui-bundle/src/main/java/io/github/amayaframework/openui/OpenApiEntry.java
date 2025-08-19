package io.github.amayaframework.openui;

import java.net.URI;
import java.util.Objects;

/**
 * A class representing a reference to the OpenAPI manifest. Contains the url and name of the manifest.
 */
public final class OpenApiEntry {
    private final URI uri;
    private final String name;

    /**
     * Constructs a {@link OpenApiEntry} instance with given uri and name.
     *
     * @param uri  the specified url
     * @param name the specified name
     */
    public OpenApiEntry(URI uri, String name) {
        this.uri = Objects.requireNonNull(uri);
        this.name = Objects.requireNonNull(name);
    }

    /**
     * Creates a {@link OpenApiEntry} instance with given uri and name.
     *
     * @param uri  the specified uri
     * @param name the specified name
     * @return the {@link OpenApiEntry} instance
     */
    public static OpenApiEntry of(URI uri, String name) {
        return new OpenApiEntry(uri, name);
    }

    /**
     * Gets the api name.
     *
     * @return the api name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the api uri.
     *
     * @return the api uri
     */
    public URI getURI() {
        return uri;
    }

    @Override
    public String toString() {
        return uri + ":" + name;
    }
}
