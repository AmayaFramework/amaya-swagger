package io.github.amayaframework.openui;

import java.net.URI;

/**
 * Factory for creating {@link OpenUi} instances with one or more
 * OpenAPI specification entries.
 */
public interface OpenUiFactory {

    /**
     * Creates an {@link OpenUi} with a default configuration and no
     * OpenAPI specification entries.
     *
     * @return a new {@link OpenUi}
     */
    OpenUi create();

    /**
     * Creates an {@link OpenUi} configured with a single specification.
     *
     * @param uri the URI of the OpenAPI definition
     * @return a new {@link OpenUi}
     */
    OpenUi create(URI uri);

    /**
     * Creates an {@link OpenUi} configured with a single specification entry.
     *
     * @param entry the OpenAPI entry
     * @return a new {@link OpenUi}
     */
    OpenUi create(OpenApiEntry entry);

    /**
     * Creates an {@link OpenUi} configured with multiple specification entries.
     *
     * @param entries the OpenAPI entries
     * @return a new {@link OpenUi}
     */
    OpenUi create(OpenApiEntry... entries);

    /**
     * Creates an {@link OpenUi} configured with multiple specification entries.
     *
     * @param entries iterable collection of entries
     * @return a new {@link OpenUi}
     */
    OpenUi create(Iterable<OpenApiEntry> entries);
}
