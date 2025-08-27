package io.github.amayaframework.compress;

import java.util.Map;

public abstract class AbstractEncodingNegotiator implements EncodingNegotiator {
    protected final EncodingManager manager;
    protected final EncodingHeaderParser parser;
    protected final Map<String, Float> priority;

    protected AbstractEncodingNegotiator(EncodingManager manager,
                                         EncodingHeaderParser parser,
                                         Map<String, Float> priority) {
        this.manager = manager;
        this.parser = parser;
        this.priority = priority;
    }

    protected Encoder select(String header) {
        return manager.select(parser.parse(header, priority));
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
