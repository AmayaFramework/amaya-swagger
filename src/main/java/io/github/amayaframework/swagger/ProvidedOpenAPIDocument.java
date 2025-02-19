package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Function0;

import java.io.InputStream;
import java.net.URI;

public final class ProvidedOpenAPIDocument extends AbstractOpenAPIDocument {
    private final Function0<InputStream> provider;

    public ProvidedOpenAPIDocument(URI path, String title, Function0<InputStream> provider) {
        super(path, title);
        this.provider = provider;
    }

    @Override
    public Function0<InputStream> getProvider() {
        return provider;
    }
}
