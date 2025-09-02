package io.github.amayaframework.compress;

import java.io.OutputStream;

/**
 * A {@link CompressEncoder} implementation for the
 * <em>identity</em> encoding.
 * <p>
 * This encoder performs no compression and returns the original
 * {@link OutputStream} unchanged. It represents the default
 * HTTP behavior when no content encoding is applied.
 */
public final class IdentityEncoder implements CompressEncoder {

    /**
     * Singleton instance of the identity encoder.
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
