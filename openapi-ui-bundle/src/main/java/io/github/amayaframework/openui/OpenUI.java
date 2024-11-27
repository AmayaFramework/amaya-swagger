package io.github.amayaframework.openui;

import java.io.InputStream;

/**
 * An interface describing an abstract bundle containing parts of the web interface for the Open API.
 */
public interface OpenUI {

    /**
     * Gets the name of index page.
     *
     * @return the name of index page
     */
    String getIndex();

    /**
     * Gets an {@link Iterable} instance containing names of all bundle parts.
     *
     * @return an {@link Iterable} instance containing names of all bundle parts
     */
    Iterable<String> getParts();

    /**
     * Gets {@link InputStream} for the specified bundle part.
     *
     * @param part the name of the bundle part
     * @return the {@link InputStream} instance
     */
    InputStream getInputStream(String part);
}
