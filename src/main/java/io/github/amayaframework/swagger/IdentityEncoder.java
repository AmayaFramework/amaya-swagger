package io.github.amayaframework.swagger;

import java.io.OutputStream;

public final class IdentityEncoder implements Encoder {
    public static final Encoder INSTANCE = new IdentityEncoder();

    private IdentityEncoder() {
    }

    @Override
    public String name() {
        return "identity";
    }

    @Override
    public OutputStream encode(OutputStream stream) {
        return stream;
    }
}
