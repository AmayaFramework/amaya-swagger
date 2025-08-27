package io.github.amayaframework.swagger;

import java.net.URI;

/**
 * TODO
 */
public final class StreamApiSource extends AbstractApiSource {
    private final OpenApiFormat format;
    private final InputStreamProvider provider;

    /**
     * TODO
     * @param uri
     * @param name
     * @param format
     * @param provider
     */
    public StreamApiSource(URI uri, String name, OpenApiFormat format, InputStreamProvider provider) {
        super(uri, name);
        this.format = format;
        this.provider = provider;
    }

    @Override
    public OpenApiFormat format() {
        return format;
    }

    @Override
    public InputStreamProvider provider() {
        return provider;
    }
}
