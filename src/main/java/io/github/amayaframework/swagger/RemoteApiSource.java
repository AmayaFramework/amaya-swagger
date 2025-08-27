package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Exceptions;

import java.net.URI;
import java.net.URL;

public final class RemoteApiSource extends AbstractApiSource {
    private final URL url;

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
