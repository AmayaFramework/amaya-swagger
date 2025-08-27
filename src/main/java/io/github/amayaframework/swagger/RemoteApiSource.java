package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Exceptions;

import java.net.URI;
import java.net.URL;

/**
 * TODO
 */
public final class RemoteApiSource extends AbstractApiSource {
    private final URL url;

    /**
     * TODO
     * @param uri
     * @param name
     */
    public RemoteApiSource(URI uri, String name) {
        super(uri, name);
        this.url = Exceptions.silent(uri::toURL);
    }

    @Override
    public OpenApiFormat format() {
        return null;
    }

    @Override
    public InputStreamProvider provider() {
        return url::openStream;
    }
}
