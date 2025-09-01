package io.github.amayaframework.compress;

import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * TODO
 */
public final class DeflateEncoder implements CompressEncoder {
    public static final int DEFAULT_BUFFER_SIZE = 512;
    private final int bufferSize;

    /**
     * TODO
     * @param bufferSize
     */
    public DeflateEncoder(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * TODO
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
