package io.github.amayaframework.compress;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public final class GzipEncoder implements Encoder {
    public static final int DEFAULT_BUFFER_SIZE = 512;
    private final int bufferSize;

    public GzipEncoder(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public GzipEncoder() {
        this(DEFAULT_BUFFER_SIZE);
    }

    @Override
    public String name() {
        return Encodings.GZIP;
    }

    @Override
    public OutputStream encode(OutputStream stream) throws IOException {
        return new GZIPOutputStream(stream, bufferSize, false);
    }
}
