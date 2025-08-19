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
     * Creates an {@link OpenUI} instance with given {@link OpenApiEntry}.
     *
     * @param root  the specified uri of the static root
     * @param entry the specified {@link OpenApiEntry} instance
     * @return the {@link OpenUI} instance
     */
    OpenUI create(URI root, OpenApiEntry entry);

    /**
     * Creates an {@link OpenUI} instance with given array of {@link OpenApiEntry}.
     *
     * @param root    the specified uri of the static root
     * @param entries the array containing specified {@link OpenApiEntry} instances
     * @return the {@link OpenUI} instance
     */
    OpenUI create(URI root, OpenApiEntry... entries);

    /**
     * Creates an {@link OpenUI} instance with given {@link Iterable} of {@link OpenApiEntry}.
     *
     * @param root    the specified uri of the static root
     * @param entries the {@link Iterable} containing specified {@link OpenApiEntry} instances
     * @return the {@link OpenUI} instance
     */
    OpenUI create(URI root, Iterable<OpenApiEntry> entries);
}
