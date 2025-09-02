package io.github.amayaframework.compress;

import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * A {@link CompressEncoder} implementation using the
 * <em>DEFLATE</em> algorithm ({@code deflate} content encoding).
 * <p>
 * Produces output compatible with HTTP {@code Content-Encoding: deflate}
 * by using a {@link Deflater} with the "nowrap" option enabled.
 */
public final class DeflateEncoder implements CompressEncoder {
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
    public DeflateEncoder(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * Creates a new encoder with the default buffer size
     * ({@link #DEFAULT_BUFFER_SIZE}).
     */
    public DeflateEncoder() {
        this(DEFAULT_BUFFER_SIZE);
    }

    @Override
    public String name() {
        return CompressEncodings.DEFLATE;
    }

    @Override
    public OutputStream encode(OutputStream stream) {
        return new DeflaterOutputStream(stream, new Deflater(Deflater.DEFAULT_COMPRESSION, true), bufferSize);
    }
}
