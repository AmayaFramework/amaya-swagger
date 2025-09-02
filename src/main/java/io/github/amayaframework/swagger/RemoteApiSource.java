package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Exceptions;

import java.net.URI;

/**
 * TODO
 */
public final class RemoteApiSource extends AbstractApiSource {
    private final InputStreamProvider provider;

    /**
     * TODO
     *
     * @param uri
     * @param name
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
