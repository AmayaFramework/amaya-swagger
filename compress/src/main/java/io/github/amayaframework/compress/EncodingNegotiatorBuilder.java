package io.github.amayaframework.compress;

import java.util.Map;

/**
 * TODO
 */
public final class EncodingNegotiatorBuilder extends AbstractEncodingNegotiatorConfigurer<EncodingNegotiatorBuilder> {

    /**
     * TODO
     */
    public static final int DEFAULT_CACHE_LIMIT = 32;

    /**
     * TODO
     */
    public static final Map<String, Float> DEFAULT_PRIORITIES = Map.of(
            Encodings.BROTLI, 1.0f,
            Encodings.GZIP, 0.9f,
            Encodings.DEFLATE, 0.8f,
            Encodings.IDENTITY, 0.001f
    );

    private int cacheLimit = DEFAULT_CACHE_LIMIT;

    @Override
    public void reset() {
        super.reset();
        cacheLimit = DEFAULT_CACHE_LIMIT;
    }

    /**
     * TODO
     * @return
     */
    public int cacheLimit() {
        return cacheLimit;
    }

    /**
     * TODO
     * @param cacheLimit
     * @return
     */
    public EncodingNegotiatorBuilder cacheLimit(int cacheLimit) {
        this.cacheLimit = cacheLimit;
        return this;
    }

    private void addDefaultEncoders(EncodingManager manager) {
        manager.ensure(Encodings.DEFLATE, DeflateEncoder::new);
        manager.ensure(Encodings.GZIP, GzipEncoder::new);
    }

    private void addEncoders(EncodingManager manager) {
        if (encoders == null) {
            addDefaultEncoders(manager);
        } else {
            encoders.values().forEach(manager::add);
        }
    }

    /**
     * TODO
     * @return
     */
    public EncodingNegotiator build() {
        var manager = this.manager == null ? new MapEncodingManager() : this.manager;
        manager.ensure(IdentityEncoder.INSTANCE);
        var parser = this.parser == null ? new SplitEncodingHeaderParser() : this.parser;
        addEncoders(manager);
        var priorities = this.priorities == null ? DEFAULT_PRIORITIES : this.priorities;
        var ret = new CachedEncodingNegotiator(manager, parser, priorities, cacheLimit);
        reset();
        return ret;
    }
}
