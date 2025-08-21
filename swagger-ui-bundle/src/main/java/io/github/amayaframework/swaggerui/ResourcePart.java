package io.github.amayaframework.swaggerui;

import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.openui.AbstractPart;

import java.io.InputStream;

final class ResourcePart extends AbstractPart {
    private final String charset;

    ResourcePart(String name, MimeType type, String charset) {
        super(name, type);
        this.charset = charset;
    }

    ResourcePart(String name, MimeType type) {
        this(name, type, null);
    }

    @Override
    public String charset() {
        return charset;
    }

    @Override
    public InputStream inputStream() {
        return ResourcePart.class.getResourceAsStream(name);
    }
}
