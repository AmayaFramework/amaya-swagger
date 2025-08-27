package io.github.amayaframework.compress;

import java.util.Map;

/**
 * TODO
 */
@FunctionalInterface
public interface EncodingHeaderParser {

    /**
     * TODO
     * @param header
     * @param priorities
     * @return
     */
    Iterable<String> parse(String header, Map<String, Float> priorities);
}
