package io.github.amayaframework.compress;

import java.util.Map;

/**
 * TODO
 */
public final class CompressNegotiatorBuilder extends AbstractCompressNegotiatorConfigurer<CompressNegotiatorBuilder> {

    /**
     * TODO
     */
    public static final int DEFAULT_CACHE_LIMIT = 32;

    /**
     * TODO
     */
    public static final Map<String, Float> DEFAULT_PRIORITIES = Map.of(
            CompressEncodings.BROTLI, 0.3f,
            CompressEncodings.GZIP, 0.2f,
            CompressEncodings.DEFLATE, 0.1f,
            CompressEncodings.IDENTITY, 0.001f
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
    public CompressNegotiatorBuilder cacheLimit(int cacheLimit) {
        this.cacheLimit = cacheLimit;
        return this;
    }

    private void addDefaultEncoders(CompressManager manager) {
        manager.ensure(CompressEncodings.DEFLATE, DeflateEncoder::new);
        manager.ensure(CompressEncodings.GZIP, GzipEncoder::new);
    }

    private void addEncoders(CompressManager manager) {
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
    public CompressNegotiator build() {
        var manager = this.manager == null ? new MapCompressManager() : this.manager;
        manager.ensure(IdentityEncoder.INSTANCE);
        var parser = this.parser == null ? new SplitEncodingHeaderParser() : this.parser;
        addEncoders(manager);
        var priorities = this.priorities == null ? DEFAULT_PRIORITIES : this.priorities;
        var ret = new CachedCompressNegotiator(manager, parser, priorities, cacheLimit);
        reset();
        return ret;
    }
}
