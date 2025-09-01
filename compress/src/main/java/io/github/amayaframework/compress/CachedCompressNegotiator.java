package io.github.amayaframework.compress;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 */
public final class CachedCompressNegotiator extends AbstractCompressNegotiator {
    private final Map<String, EncoderHolder> cache;
    private final int cacheLimit;

    /**
     * TODO
     * @param manager
     * @param parser
     * @param priority
     * @param cacheLimit
     */
    public CachedCompressNegotiator(CompressManager manager,
                                    EncodingHeaderParser parser,
                                    Map<String, Float> priority,
                                    int cacheLimit) {
        super(manager, parser, priority);
        if (cacheLimit < 1) {
            this.cache = null;
        } else {
            this.cache = new ConcurrentHashMap<>();
        }
        this.cacheLimit = cacheLimit;
    }

    @Override
    protected CompressEncoder lookup(String header) {
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
        CompressEncoder encoder;

        EncoderHolder(CompressEncoder encoder) {
            this.encoder = encoder;
        }
    }
}
