package io.github.amayaframework.compress;

/**
 * TODO
 */
@FunctionalInterface
public interface CompressNegotiator {

    /**
     * TODO
     * @param header
     * @return
     */
    CompressEncoder negotiate(String header);
}
