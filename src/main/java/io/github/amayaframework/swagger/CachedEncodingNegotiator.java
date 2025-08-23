package io.github.amayaframework.swagger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CachedEncodingNegotiator extends AbstractEncodingNegotiator {
    private final Map<String, EncoderHolder> cache;
    private final int cacheLimit;

    public CachedEncodingNegotiator(EncodingManager manager,
                                    EncodingHeaderParser parser,
                                    Encoder encoder,
                                    int cacheLimit) {
        super(manager, parser, encoder);
        if (cacheLimit < 1) {
            this.cache = null;
        } else {
            this.cache = new ConcurrentHashMap<>();
        }
        this.cacheLimit = cacheLimit;
    }

    @Override
    protected Encoder lookup(String header) {
        if (cache == null) {
            return select(header);
        }
        if (cache.size() > cacheLimit) {
            var holder = cache.get(header);
            if (holder == null) {
                return select(header);
            }
            return holder.encoder;
        }
        return cache.computeIfAbsent(header, k -> new EncoderHolder(select(k))).encoder;
    }

    private static final class EncoderHolder {
        Encoder encoder;

        EncoderHolder(Encoder encoder) {
            this.encoder = encoder;
        }
    }
}
