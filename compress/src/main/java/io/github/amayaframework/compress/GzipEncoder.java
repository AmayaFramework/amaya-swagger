package io.github.amayaframework.compress;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * A {@link CompressEncoder} implementation using the
 * <em>GZIP</em> algorithm ({@code gzip} content encoding).
 * <p>
 * Produces output compatible with HTTP {@code Content-Encoding: gzip}
 * by wrapping the provided {@link OutputStream} in a {@link GZIPOutputStream}.
 */
public final class GzipEncoder implements CompressEncoder {
    /**
     * Default buffer size used when writing compressed data.
     */
    public static final int DEFAULT_BUFFER_SIZE = 512;
    private final int bufferSize;

    /**
     * Creates a new encoder with a custom buffer size.
     *
     * @param bufferSize the buffer size in bytes, must be positive
     */
    public GzipEncoder(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * Creates a new encoder with the default buffer size
     * ({@link #DEFAULT_BUFFER_SIZE}).
     */
    public GzipEncoder() {
        this(DEFAULT_BUFFER_SIZE);
    }

    @Override
    public String name() {
        return CompressEncodings.GZIP;
    }

    @Override
    public OutputStream encode(OutputStream stream) throws IOException {
        return new GZIPOutputStream(stream, bufferSize, false);
    }
}
