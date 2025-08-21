package io.github.amayaframework.swaggerui;

import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.openui.AbstractPart;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

final class BufferPart extends AbstractPart {
    private final byte[] buffer;

    BufferPart(String name, MimeType type, byte[] buffer) {
        super(name, type);
        this.buffer = buffer;
    }

    @Override
    public String charset() {
        return "utf-8";
    }

    @Override
    public InputStream inputStream() {
        return new ByteArrayInputStream(buffer);
    }
}
