package io.github.amayaframework.compress;

import java.util.Map;

/**
 * Parses the value of an HTTP {@code Accept-Encoding} header
 * and extracts the list of supported encodings.
 * <p>
 * Implementations may also populate the provided priority map
 * with quality factors (q-values) for each encoding.
 */
@FunctionalInterface
public interface EncodingHeaderParser {

    /**
     * Parses an {@code Accept-Encoding} header.
     *
     * @param header     the raw header string
     * @param priorities a map to fill with encoding priorities (q-values)
     * @return the sequence of encodings in the order they appear
     */
    Iterable<String> parse(String header, Map<String, Float> priorities);
}
