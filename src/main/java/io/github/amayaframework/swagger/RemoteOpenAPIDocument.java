package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Function0;

import java.io.InputStream;
import java.net.URI;

/**
 *
 */
public final class RemoteOpenAPIDocument extends AbstractOpenAPIDocument {

    /**
     *
     * @param path
     * @param title
     */
    public RemoteOpenAPIDocument(URI path, String title) {
        super(path, title);
    }

    @Override
    public Function0<InputStream> getProvider() {
        return null;
    }
}
