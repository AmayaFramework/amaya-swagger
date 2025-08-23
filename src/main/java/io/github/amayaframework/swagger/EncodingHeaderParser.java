package io.github.amayaframework.swagger;

@FunctionalInterface
public interface EncodingHeaderParser {

    Iterable<String> parse(String header);
}
