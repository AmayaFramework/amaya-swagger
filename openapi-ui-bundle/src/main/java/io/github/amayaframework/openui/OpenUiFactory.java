package io.github.amayaframework.openui;

import java.net.URI;

/**
 * An interface describing an abstract {@link OpenUi} factory.
 */
public interface OpenUiFactory {

    OpenUi create();

    OpenUi create(URI uri);

    OpenUi create(OpenApiEntry entry);

    OpenUi create(OpenApiEntry... entries);

    OpenUi create(Iterable<OpenApiEntry> entries);
}
