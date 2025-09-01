package io.github.amayaframework.compress;

import java.io.OutputStream;

/**
 * TODO
 */
public final class IdentityEncoder implements CompressEncoder {

    /**
     * TODO
     */
    public static final CompressEncoder INSTANCE = new IdentityEncoder();

    private IdentityEncoder() {
    }

    @Override
    public String name() {
        return CompressEncodings.IDENTITY;
    }

    @Override
    public OutputStream encode(OutputStream stream) {
        return stream;
    }
}
