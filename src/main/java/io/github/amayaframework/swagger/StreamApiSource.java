package io.github.amayaframework.swagger;

import java.net.URI;

/**
 * An {@link OpenApiSource} backed by a custom {@link InputStreamProvider}.
 * <p>
 * This implementation is useful for embedding OpenAPI documents
 * directly from application resources, memory buffers, or other
 * non-remote sources.
 */
public final class StreamApiSource extends AbstractApiSource {
    private final OpenApiFormat format;
    private final String charset;
    private final InputStreamProvider provider;

    /**
     * Creates a new stream API source with full metadata.
     *
     * @param uri      the URI identifying this source
     * @param name     a human-readable name for this source
     * @param format   the OpenAPI format (YAML or JSON)
     * @param charset  the character set used to encode the document
     * @param provider the input stream provider
     */
    public StreamApiSource(URI uri, String name, OpenApiFormat format, String charset, InputStreamProvider provider) {
        super(uri, name);
        this.format = format;
        this.charset = charset;
        this.provider = provider;
    }

    /**
     * Creates a new stream API source with a default charset of {@code utf-8}.
     *
     * @param uri      the URI identifying this source
     * @param name     a human-readable name for this source
     * @param format   the OpenAPI format (YAML or JSON)
     * @param provider the input stream provider
     */
    public StreamApiSource(URI uri, String name, OpenApiFormat format, InputStreamProvider provider) {
        this(uri, name, format, "utf-8", provider);
    }

    @Override
    public OpenApiFormat format() {
        return format;
    }

    @Override
    public String charset() {
        return charset;
    }

    @Override
    public InputStreamProvider provider() {
        return provider;
    }
}
