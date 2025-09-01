package io.github.amayaframework.compress;

import java.util.Map;

/**
 *
 */
public abstract class AbstractCompressNegotiator implements CompressNegotiator {
    protected final CompressManager manager;
    protected final EncodingHeaderParser parser;
    protected final Map<String, Float> priority;

    /**
     * TODO
     * @param manager
     * @param parser
     * @param priority
     */
    protected AbstractCompressNegotiator(CompressManager manager,
                                         EncodingHeaderParser parser,
                                         Map<String, Float> priority) {
        this.manager = manager;
        this.parser = parser;
        this.priority = priority;
    }

    /**
     * TODO
     * @param header
     * @return
     */
    protected CompressEncoder select(String header) {
        return manager.select(parser.parse(header, priority));
    }

    /**
     * TODO
     * @param header
     * @return
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
