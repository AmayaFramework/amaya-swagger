package io.github.amayaframework.compress;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A {@link CompressNegotiator} implementation with caching support.
 * <p>
 * Uses a {@link ConcurrentHashMap} to cache results of previous
 * {@code Accept-Encoding} header lookups, reducing parsing and
 * negotiation overhead for repeated requests.
 * <ul>
 *   <li>If {@code cacheLimit < 1}, caching is disabled and lookups
 *   are performed on every call.</li>
 *   <li>If the cache size exceeds {@code cacheLimit}, new headers
 *   are resolved without adding them to the cache.</li>
 * </ul>
 */
public final class CachedCompressNegotiator extends AbstractCompressNegotiator {
    private final Map<String, EncoderHolder> cache;
    private final int cacheLimit;

    /**
     * Creates a negotiator with caching support.
     *
     * @param manager    the encoder manager
     * @param parser     the header parser
     * @param priority   optional priority overrides
     * @param cacheLimit maximum number of cached entries,
     *                   or a value < 1 to disable caching
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

    /**
     * Clears the internal cache of negotiated encoders.
     * <p>
     * After calling this method, all previously cached
     * {@code Accept-Encoding} results are discarded and
     * later lookups will be resolved again.
     * <p>
     * Does nothing if caching is disabled.
     */
    public void reset() {
        if (cache != null) {
            cache.clear();
        }
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

    /**
     * Internal wrapper to allow safe null values in the cache.
     */
    private static final class EncoderHolder {
        CompressEncoder encoder;

        EncoderHolder(CompressEncoder encoder) {
            this.encoder = encoder;
        }
    }
}
