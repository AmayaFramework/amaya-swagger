package io.github.amayaframework.openui;

import java.net.URI;
import java.util.Objects;

/**
 * A class representing a reference to the OpenAPI manifest. Contains the url and name of the manifest.
 */
public final class ApiEntry {
    private final URI uri;
    private final String name;

    /**
     * Constructs a {@link ApiEntry} instance with given uri and name.
     *
     * @param uri  the specified url
     * @param name the specified name
     */
    public ApiEntry(URI uri, String name) {
        this.uri = Objects.requireNonNull(uri);
        this.name = Objects.requireNonNull(name);
    }

    /**
     * Creates a {@link ApiEntry} instance with given uri and name.
     *
     * @param uri  the specified uri
     * @param name the specified name
     * @return the {@link ApiEntry} instance
     */
    public static ApiEntry of(URI uri, String name) {
        return new ApiEntry(uri, name);
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
