package io.github.amayaframework.compress;

import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public final class DeflateEncoder implements Encoder {
    private static final int DEFAULT_BUFFER_SIZE = 512;
    private final int bufferSize;

    public DeflateEncoder(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public DeflateEncoder() {
        this(DEFAULT_BUFFER_SIZE);
    }

    @Override
    public String name() {
        return Encodings.DEFLATE;
    }

    @Override
    public OutputStream encode(OutputStream stream) {
        return new DeflaterOutputStream(stream, new Deflater(Deflater.DEFAULT_COMPRESSION, true), bufferSize);
    }
}
