package io.github.amayaframework.compress;

import java.util.Map;

/**
 * Parses the value of an HTTP {@code Accept-Encoding} header
 * and produces an ordered list of supported encodings.
 * <p>
 * Quality factors (<em>q-values</em>) from the header are taken
 * into account when ordering. In addition, the caller may supply
 * a map of server-side priorities, which are used to break ties
 * when two encodings have the same q-value.
 */
@FunctionalInterface
public interface EncodingHeaderParser {

    /**
     * Parses an {@code Accept-Encoding} header.
     *
     * @param header     the raw header string
     * @param priorities optional server-side priorities, used only
     *                   for tiebreaking between encodings with the
     *                   same q-value; may be {@code null}
     * @return the sequence of encodings ordered by preference
     */
    Iterable<String> parse(String header, Map<String, Float> priorities);

    /**
     * Parses an {@code Accept-Encoding} header without any server-side
     * priority overrides. Ordering is determined solely by the q-values
     * from the header (and the default treatment of {@code identity}).
     *
     * @param header the raw header string
     * @return the sequence of encodings ordered by preference
     */
    default Iterable<String> parse(String header) {
        return parse(header, null);
    }
}
