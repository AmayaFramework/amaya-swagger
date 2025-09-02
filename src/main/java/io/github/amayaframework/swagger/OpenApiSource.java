package io.github.amayaframework.swagger;

import java.net.URI;

/**
 * Represents a source of an OpenAPI specification document.
 * <p>
 * Implementations may provide metadata such as name, format,
 * charset, and a way to get the raw document stream.
 */
public interface OpenApiSource {

    /**
     * Returns the URI of the OpenAPI document.
     *
     * @return the document URI
     */
    URI uri();

    /**
     * Returns a human-readable name of this source.
     *
     * @return the source name
     */
    String name();

    /**
     * Returns the format of the OpenAPI document
     * (e.g. {@link OpenApiFormat#YAML} or {@link OpenApiFormat#JSON}).
     *
     * @return the document format
     */
    OpenApiFormat format();

    /**
     * Returns the character set used to encode the document,
     * or {@code null} if unspecified.
     *
     * @return the charset, or {@code null}
     */
    String charset();

    /**
     * Returns a provider capable of supplying an input stream
     * for reading the OpenAPI document.
     *
     * @return the input stream provider
     */
    InputStreamProvider provider();
}
