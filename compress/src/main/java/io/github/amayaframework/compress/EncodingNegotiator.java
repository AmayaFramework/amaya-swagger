package io.github.amayaframework.compress;

/**
 * TODO
 */
@FunctionalInterface
public interface EncodingNegotiator {

    /**
     * TODO
     * @param header
     * @return
     */
    Encoder negotiate(String header);
}
