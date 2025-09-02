package io.github.amayaframework.swagger;

import java.net.URI;

/**
 * TODO
 */
public final class StreamApiSource extends AbstractApiSource {
    private final OpenApiFormat format;
    private final String charset;
    private final InputStreamProvider provider;

    /**
     * TODO
     *
     * @param uri
     * @param name
     * @param format
     * @param charset
     * @param provider
     */
    public StreamApiSource(URI uri, String name, OpenApiFormat format, String charset, InputStreamProvider provider) {
        super(uri, name);
        this.format = format;
        this.charset = charset;
        this.provider = provider;
    }

    /**
     * TODO
     *
     * @param uri
     * @param name
     * @param format
     * @param provider
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
