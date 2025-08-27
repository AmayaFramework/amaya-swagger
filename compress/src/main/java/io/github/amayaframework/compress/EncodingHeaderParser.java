package io.github.amayaframework.compress;

import java.util.Map;

@FunctionalInterface
public interface EncodingHeaderParser {

    Iterable<String> parse(String header, Map<String, Float> priority);
}
