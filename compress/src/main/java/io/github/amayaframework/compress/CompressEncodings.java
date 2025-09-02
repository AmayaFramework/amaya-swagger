package io.github.amayaframework.compress;

/**
 * Common constants for standard HTTP content encodings.
 * <p>
 * These names correspond to values used in the
 * {@code Content-Encoding} and {@code Accept-Encoding} headers.
 */
public final class CompressEncodings {
    private CompressEncodings() {
    }

    /**
     * No encoding; the identity of the resource is preserved.
     */
    public static final String IDENTITY = "identity";

    /**
     * GZIP compression (RFC 1952).
     */
    public static final String GZIP = "gzip";

    /**
     * DEFLATE compression (RFC 1951).
     */
    public static final String DEFLATE = "deflate";

    /**
     * Brotli compression (RFC 7932).
     */
    public static final String BROTLI = "br";
}
