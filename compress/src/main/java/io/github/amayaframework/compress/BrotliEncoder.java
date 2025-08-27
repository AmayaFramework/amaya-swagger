package io.github.amayaframework.compress;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import com.aayushatharva.brotli4j.encoder.BrotliOutputStream;
import com.aayushatharva.brotli4j.encoder.Encoder.Parameters;

import java.io.IOException;
import java.io.OutputStream;

public final class BrotliEncoder implements Encoder {
    private static final int DEFAULT_BUFFER_SIZE = 16384;
    private final Parameters parameters;
    private final int bufferSize;

    public BrotliEncoder(Parameters parameters, int bufferSize) {
        this.parameters = parameters;
        this.bufferSize = bufferSize;
    }

    public BrotliEncoder(int bufferSize) {
        this(new Parameters(), bufferSize);
    }

    public BrotliEncoder() {
        this(new Parameters(), DEFAULT_BUFFER_SIZE);
    }

    public static BrotliEncoder create(Parameters parameters, int bufferSize) {
        if (Brotli4jLoader.isAvailable()) {
            return new BrotliEncoder(parameters, bufferSize);
        }
        return null;
    }

    public static BrotliEncoder create(int bufferSize) {
        return create(new Parameters(), bufferSize);
    }

    public static BrotliEncoder create() {
        return create(new Parameters(), DEFAULT_BUFFER_SIZE);
    }

    @Override
    public String name() {
        return Encodings.BROTLI;
    }

    @Override
    public OutputStream encode(OutputStream stream) throws IOException {
        return new BrotliOutputStream(stream, parameters, bufferSize);
    }
}
