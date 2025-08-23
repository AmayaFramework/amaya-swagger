package io.github.amayaframework.swagger;

import java.io.OutputStream;

public abstract class AbstractEncodingNegotiator implements EncodingNegotiator {
    protected final EncodingManager manager;
    protected final EncodingHeaderParser parser;
    protected final Encoder encoder;

    protected AbstractEncodingNegotiator(EncodingManager manager, EncodingHeaderParser parser, Encoder encoder) {
        this.manager = manager;
        this.parser = parser;
        this.encoder = encoder;
    }

    protected Encoder select(String header) {
        return manager.select(parser.parse(header));
    }

    protected abstract Encoder lookup(String header);

    @Override
    public Encoder negotiate(String header) {
        if (header == null || header.isBlank()) {
            return IdentityEncoder.INSTANCE;
        }
        return lookup(header);
    }
}
