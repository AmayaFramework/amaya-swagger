package io.github.amayaframework.openui;

import java.net.URI;

/**
 * An interface describing an abstract {@link OpenUI} factory.
 */
public interface OpenUIFactory {

    /**
     * Creates an {@link OpenUI} instance with given uri.
     *
     * @param root the specified uri of the static root
     * @param uri  the specified uri of the open api manifest
     * @return the {@link OpenUI} instance
     */
    OpenUI create(URI root, URI uri);

    /**
     * Creates an {@link OpenUI} instance with given {@link ApiEntry}.
     *
     * @param root  the specified uri of the static root
     * @param entry the specified {@link ApiEntry} instance
     * @return the {@link OpenUI} instance
     */
    OpenUI create(URI root, ApiEntry entry);

    /**
     * Creates an {@link OpenUI} instance with given array of {@link ApiEntry}.
     *
     * @param root    the specified uri of the static root
     * @param entries the array containing specified {@link ApiEntry} instances
     * @return the {@link OpenUI} instance
     */
    OpenUI create(URI root, ApiEntry... entries);

    /**
     * Creates an {@link OpenUI} instance with given {@link Iterable} of {@link ApiEntry}.
     *
     * @param root    the specified uri of the static root
     * @param entries the {@link Iterable} containing specified {@link ApiEntry} instances
     * @return the {@link OpenUI} instance
     */
    OpenUI create(URI root, Iterable<ApiEntry> entries);
}
