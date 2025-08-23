package io.github.amayaframework.swagger;

public interface EncodingManager {

    Encoder get(String encoding);

    void add(Encoder encoder);

    Encoder remove(String encoding);

    Encoder select(Iterable<String> encodings);
}
