package io.github.amayaframework.openui;

import java.net.URI;

/**
 * TODO
 */
public interface OpenUiFactory {

    /**
     * TODO
     * @return
     */
    OpenUi create();

    /**
     * TODO
     * @param uri
     * @return
     */
    OpenUi create(URI uri);

    /**
     * TODO
     * @param entry
     * @return
     */
    OpenUi create(OpenApiEntry entry);

    /**
     * TODO
     * @param entries
     * @return
     */
    OpenUi create(OpenApiEntry... entries);

    /**
     * TODO
     * @param entries
     * @return
     */
    OpenUi create(Iterable<OpenApiEntry> entries);
}
