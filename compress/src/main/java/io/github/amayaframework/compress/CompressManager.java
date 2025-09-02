package io.github.amayaframework.compress;

import java.util.function.Supplier;

/**
 * Manages a registry of {@link CompressEncoder} implementations.
 * Provides lookup, registration, and selection facilities for
 * server-side content negotiation.
 */
public interface CompressManager {

    /**
     * Returns an encoder for the given encoding name.
     *
     * @param encoding the encoding name
     * @return the encoder, or {@code null} if not registered
     */
    CompressEncoder get(String encoding);

    /**
     * Checks whether an encoder for the given encoding is registered.
     *
     * @param encoding the encoding name
     * @return {@code true} if present, otherwise {@code false}
     */
    boolean contains(String encoding);

    /**
     * Adds a new encoder to the registry, possibly replacing
     * an existing one with the same name.
     *
     * @param encoder the encoder to register
     */
    void add(CompressEncoder encoder);

    /**
     * Ensures that the given encoder is present in the registry,
     * adding it only if not already registered.
     *
     * @param encoder the encoder to ensure
     */
    void ensure(CompressEncoder encoder);

    /**
     * Ensures that an encoder with the given name exists in the registry,
     * lazily creating it from the supplied factory if missing.
     *
     * @param name     the encoding name
     * @param supplier the encoder supplier
     */
    void ensure(String name, Supplier<CompressEncoder> supplier);

    /**
     * Removes the encoder associated with the given encoding name.
     *
     * @param encoding the encoding name
     * @return the removed encoder, or {@code null} if none was present
     */
    CompressEncoder remove(String encoding);

    /**
     * Selects the best matching encoder from the given set of encodings,
     * according to server preferences and registered encoders.
     *
     * @param encodings an ordered list of acceptable encodings
     * @return the selected encoder, or {@code null} if none match
     */
    CompressEncoder select(Iterable<String> encodings);
}
