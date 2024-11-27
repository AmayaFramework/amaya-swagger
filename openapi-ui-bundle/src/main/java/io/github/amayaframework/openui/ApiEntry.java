package io.github.amayaframework.openui;

import java.net.URL;
import java.util.Objects;

/**
 * A class representing a reference to the OpenAPI manifest. Contains the url and name of the manifest.
 */
public final class ApiEntry {
    private final URL url;
    private final String name;

    /**
     * Constructs a {@link ApiEntry} instance with given url and name.
     *
     * @param url  the specified url
     * @param name the specified name
     */
    public ApiEntry(URL url, String name) {
        this.url = Objects.requireNonNull(url);
        this.name = Objects.requireNonNull(name);
    }

    /**
     * Creates a {@link ApiEntry} instance with given url and name.
     *
     * @param url  the specified url
     * @param name the specified name
     * @return the {@link ApiEntry} instance
     */
    public static ApiEntry of(URL url, String name) {
        return new ApiEntry(url, name);
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
     * Gets the api url.
     *
     * @return the api url
     */
    public URL getURL() {
        return url;
    }

    @Override
    public String toString() {
        return url + ":" + name;
    }
}
