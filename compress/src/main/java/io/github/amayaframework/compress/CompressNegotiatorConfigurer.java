package io.github.amayaframework.compress;

import java.util.Collection;
import java.util.Map;

/**
 * Configurer interface for building and customizing a {@link CompressNegotiator}.
 * <p>
 * Provides methods to set the {@link EncodingHeaderParser}, {@link CompressManager},
 * available {@link CompressEncoder encoders}, and their relative priorities.
 * <p>
 * Implementations are expected to be fluent and return {@code this}
 * from mutator methods to allow chaining.
 */
public interface CompressNegotiatorConfigurer {

    /**
     * Resets this configurer to its initial state, discarding all
     * previously added encoders, parser, manager, and priorities.
     */
    void reset();

    /**
     * Returns the currently configured {@link EncodingHeaderParser}.
     *
     * @return the parser instance, or {@code null} if not set
     */
    EncodingHeaderParser parser();

    /**
     * Sets the {@link EncodingHeaderParser} to be used.
     *
     * @param parser the parser instance
     * @return this configurer for chaining
     */
    CompressNegotiatorConfigurer parser(EncodingHeaderParser parser);

    /**
     * Returns the currently configured {@link CompressManager}.
     *
     * @return the manager instance, or {@code null} if not set
     */
    CompressManager manager();

    /**
     * Sets the {@link CompressManager} to be used.
     *
     * @param manager the manager instance
     * @return this configurer for chaining
     */
    CompressNegotiatorConfigurer manager(CompressManager manager);

    /**
     * Returns the collection of encoders currently registered in this configurer.
     *
     * @return collection of encoders
     */
    Collection<CompressEncoder> encoders();

    /**
     * Adds an encoder with a specific priority (q-value).
     *
     * @param encoder  the encoder to add
     * @param priority the priority value in the range {@code [0.0, 1.0]}
     * @return this configurer for chaining
     */
    CompressNegotiatorConfigurer addEncoder(CompressEncoder encoder, Float priority);

    /**
     * Adds an encoder with no priority.
     *
     * @param encoder the encoder to add
     * @return this configurer for chaining
     */
    CompressNegotiatorConfigurer addEncoder(CompressEncoder encoder);

    /**
     * Removes an encoder by its name.
     *
     * @param name the encoding name (e.g. {@code "gzip"})
     * @return this configurer for chaining
     */
    CompressNegotiatorConfigurer removeEncoder(String name);

    /**
     * Returns the current map of encoder priorities (q-values).
     *
     * @return a map of encoding names to priority values
     */
    Map<String, Float> priorities();

    /**
     * Replaces the priority map with a new one.
     *
     * @param priorities the new map of encoding priorities
     * @return this configurer for chaining
     */
    CompressNegotiatorConfigurer priorities(Map<String, Float> priorities);

    /**
     * Sets the priority (q-value) for a specific encoding.
     *
     * @param encoding the encoding name
     * @param value    the priority value in the range {@code [0.0, 1.0]}
     * @return this configurer for chaining
     */
    CompressNegotiatorConfigurer priority(String encoding, Float value);
}
