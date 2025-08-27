package io.github.amayaframework.compress;

import java.util.function.Supplier;

public interface EncodingManager {

    Encoder get(String encoding);

    boolean contains(String encoding);

    void add(Encoder encoder);

    void ensure(Encoder encoder);

    void ensure(String name, Supplier<Encoder> supplier);

    Encoder remove(String encoding);

    Encoder select(Iterable<String> encodings);
}
