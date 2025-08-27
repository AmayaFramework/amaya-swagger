package io.github.amayaframework.compress;

import java.util.function.Supplier;

/**
 * TODO
 */
public interface EncodingManager {

    /**
     * TODO
     * @param encoding
     * @return
     */
    Encoder get(String encoding);

    /**
     * TODO
     * @param encoding
     * @return
     */
    boolean contains(String encoding);

    /**
     * TODO
     * @param encoder
     */
    void add(Encoder encoder);

    /**
     * TODO
     * @param encoder
     */
    void ensure(Encoder encoder);

    /**
     * TODO
     * @param name
     * @param supplier
     */
    void ensure(String name, Supplier<Encoder> supplier);

    /**
     * TODO
     * @param encoding
     * @return
     */
    Encoder remove(String encoding);

    /**
     * TODO
     * @param encodings
     * @return
     */
    Encoder select(Iterable<String> encodings);
}
