package io.github.amayaframework.compress;

@FunctionalInterface
public interface EncodingNegotiator {

    Encoder negotiate(String header);
}
