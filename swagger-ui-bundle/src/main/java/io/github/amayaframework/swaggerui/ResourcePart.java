package io.github.amayaframework.swaggerui;

import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.openui.AbstractPart;

import java.io.InputStream;

final class ResourcePart extends AbstractPart {

    ResourcePart(String name, MimeType type) {
        super(name, type);
    }

    @Override
    public InputStream inputStream() {
        return Util.getInputStream(name);
    }
}
