package io.github.amayaframework.compress;

import java.io.OutputStream;

public final class IdentityEncoder implements Encoder {
    public static final Encoder INSTANCE = new IdentityEncoder();

    private IdentityEncoder() {
    }

    @Override
    public String name() {
        return Encodings.IDENTITY;
    }

    @Override
    public OutputStream encode(OutputStream stream) {
        return stream;
    }
}
