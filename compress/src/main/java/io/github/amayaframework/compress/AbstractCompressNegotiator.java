package io.github.amayaframework.compress;

import java.util.Map;

/**
 * Base implementation of {@link CompressNegotiator} that integrates a
 * {@link CompressManager} with an {@link EncodingHeaderParser}.
 * <p>
 * Provides common logic for parsing {@code Accept-Encoding} headers,
 * applying priority rules, and selecting the most suitable encoder.
 * Subclasses implement {@link #lookup(String)} to define caching or
 * custom lookup strategies.
 */
public abstract class AbstractCompressNegotiator implements CompressNegotiator {
    protected final CompressManager manager;
    protected final EncodingHeaderParser parser;
    protected final Map<String, Float> priority;

    /**
     * Creates a new negotiator with the given manager, parser, and priorities.
     *
     * @param manager  the encoder manager to use for selection
     * @param parser   the parser for {@code Accept-Encoding} headers
     * @param priority optional priority overrides for specific encodings
     */
    protected AbstractCompressNegotiator(CompressManager manager,
                                         EncodingHeaderParser parser,
                                         Map<String, Float> priority) {
        this.manager = manager;
        this.parser = parser;
        this.priority = priority;
    }

    /**
     * Selects an encoder by parsing the given header and delegating
     * to the underlying {@link CompressManager}.
     *
     * @param header the {@code Accept-Encoding} header value
     * @return the best matching encoder, or {@code null} if none match
     */
    protected CompressEncoder select(String header) {
        return manager.select(parser.parse(header, priority));
    }

    /**
     * Performs a lookup for the encoder corresponding to the given header.
     * Implementations may add caching or additional strategies.
     *
     * @param header the {@code Accept-Encoding} header value
     * @return the selected encoder, or {@code null} if none match
     */
    protected abstract CompressEncoder lookup(String header);

    @Override
    public CompressEncoder negotiate(String header) {
        if (header == null || header.isBlank()) {
            return IdentityEncoder.INSTANCE;
        }
        return lookup(header);
    }
}
