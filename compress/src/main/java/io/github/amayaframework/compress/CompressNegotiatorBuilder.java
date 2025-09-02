package io.github.amayaframework.compress;

import java.util.Map;

/**
 * Builder for creating configured instances of {@link CompressNegotiator}.
 * <p>
 * Provides a fluent API for setting the header parser, encoder manager,
 * encoders, priorities, and cache behavior. If not explicitly configured,
 * sensible defaults are applied:
 * <ul>
 *   <li>{@link SplitEncodingHeaderParser} for parsing {@code Accept-Encoding}</li>
 *   <li>{@link MapCompressManager} for encoder storage</li>
 *   <li>{@link IdentityEncoder}, {@link GzipEncoder}, and {@link DeflateEncoder}
 *       as the default encoders</li>
 *   <li>{@link #DEFAULT_PRIORITIES default priorities} favoring Brotli > GZIP > Deflate > Identity</li>
 *   <li>{@link #DEFAULT_CACHE_LIMIT} as the maximum number of cached lookups</li>
 * </ul>
 * <p>
 * Each call to {@link #build()} returns a new {@link CachedCompressNegotiator}
 * and resets this builder back to its defaults.
 */
public final class CompressNegotiatorBuilder extends AbstractCompressNegotiatorConfigurer<CompressNegotiatorBuilder> {

    /**
     * Default maximum number of cached header lookups.
     */
    public static final int DEFAULT_CACHE_LIMIT = 32;

    /**
     * Default priority values (q-values) for encodings.
     * <p>
     * Brotli is preferred, followed by GZIP, Deflate,
     * and finally Identity as a fallback.
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
     * Returns the current cache limit used for header negotiation.
     *
     * @return the maximum number of cached entries
     */
    public int cacheLimit() {
        return cacheLimit;
    }

    /**
     * Sets the maximum number of cached header lookups.
     * A value less than {@code 1} disables caching entirely.
     *
     * @param cacheLimit the maximum number of cached entries
     * @return this builder for chaining
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
     * Builds a new {@link CompressNegotiator} instance using the
     * configured values or defaults where none were set.
     * <p>
     * After building, this builder is reset to its defaults so it
     * can be reused independently for the next configuration.
     *
     * @return a fully configured {@link CompressNegotiator}
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
