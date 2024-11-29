package io.github.amayaframework.openui;

import java.net.URI;

/**
 * An interface describing an abstract {@link OpenUI} factory.
 */
public interface OpenUIFactory {

    /**
     * Creates an {@link OpenUI} instance with given uri.
     *
     * @param uri the specified url of the open api manifest.
     * @return the {@link OpenUI} instance
     */
    OpenUI create(URI uri);

    /**
     * Creates an {@link OpenUI} instance with given {@link ApiEntry}.
     *
     * @param entry the specified {@link ApiEntry} instance
     * @return the {@link OpenUI} instance
     */
    OpenUI create(ApiEntry entry);

    /**
     * Creates an {@link OpenUI} instance with given array of {@link ApiEntry}.
     *
     * @param entries the array containing specified {@link ApiEntry} instances
     * @return the {@link OpenUI} instance
     */
    OpenUI create(ApiEntry... entries);

    /**
     * Creates an {@link OpenUI} instance with given {@link Iterable} of {@link ApiEntry}.
     *
     * @param entries the {@link Iterable} containing specified {@link ApiEntry} instances
     * @return the {@link OpenUI} instance
     */
    OpenUI create(Iterable<ApiEntry> entries);
}
