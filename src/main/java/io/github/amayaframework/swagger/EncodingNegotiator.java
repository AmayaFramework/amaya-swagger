package io.github.amayaframework.swagger;

@FunctionalInterface
public interface EncodingNegotiator {

    Encoder negotiate(String header);
}
