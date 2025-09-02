package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Exceptions;

import java.net.URI;

/**
 * An {@link OpenApiSource} backed by a remote URI.
 * <p>
 * The {@link InputStreamProvider} is created from the URI's URL
 * and opens a new network stream on each access.
 * <p>
 * Format and charset are not determined automatically and
 * therefore return {@code null}.
 */
public final class RemoteApiSource extends AbstractApiSource {
    private final InputStreamProvider provider;

    /**
     * Creates a new remote API source.
     *
     * @param uri  the remote URI pointing to the OpenAPI document
     * @param name a human-readable name for this source
     */
    public RemoteApiSource(URI uri, String name) {
        super(uri, name);
        this.provider = Exceptions.silent(uri::toURL)::openStream;
    }

    @Override
    public OpenApiFormat format() {
        return null;
    }

    @Override
    public String charset() {
        return null;
    }

    @Override
    public InputStreamProvider provider() {
        return provider;
    }
}
