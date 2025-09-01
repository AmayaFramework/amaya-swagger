package io.github.amayaframework.compress;

import java.util.function.Supplier;

/**
 * TODO
 */
public interface CompressManager {

    /**
     * TODO
     * @param encoding
     * @return
     */
    CompressEncoder get(String encoding);

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
    void add(CompressEncoder encoder);

    /**
     * TODO
     * @param encoder
     */
    void ensure(CompressEncoder encoder);

    /**
     * TODO
     * @param name
     * @param supplier
     */
    void ensure(String name, Supplier<CompressEncoder> supplier);

    /**
     * TODO
     * @param encoding
     * @return
     */
    CompressEncoder remove(String encoding);

    /**
     * TODO
     * @param encodings
     * @return
     */
    CompressEncoder select(Iterable<String> encodings);
}
