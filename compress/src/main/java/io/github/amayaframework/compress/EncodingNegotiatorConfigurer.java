package io.github.amayaframework.compress;

import java.util.Collection;
import java.util.Map;

/**
 * TODO
 */
public interface EncodingNegotiatorConfigurer {

    /**
     * TODO
     */
    void reset();

    /**
     * TODO
     * @return
     */
    EncodingHeaderParser parser();

    /**
     * TODO
     * @param parser
     * @return
     */
    EncodingNegotiatorConfigurer parser(EncodingHeaderParser parser);

    /**
     * TODO
     * @return
     */
    EncodingManager manager();

    /**
     * TODO
     * @param manager
     * @return
     */
    EncodingNegotiatorConfigurer manager(EncodingManager manager);

    /**
     * TODO
     * @return
     */
    Collection<Encoder> encoders();

    /**
     * TODO
     * @param encoder
     * @param priority
     * @return
     */
    EncodingNegotiatorConfigurer addEncoder(Encoder encoder, Float priority);

    /**
     * TODO
     * @param encoder
     * @return
     */
    EncodingNegotiatorConfigurer addEncoder(Encoder encoder);

    /**
     * TODO
     * @param name
     * @return
     */
    EncodingNegotiatorConfigurer removeEncoder(String name);

    /**
     * TODO
     * @return
     */
    Map<String, Float> priorities();

    /**
     * TODO
     * @param priorities
     * @return
     */
    EncodingNegotiatorConfigurer priorities(Map<String, Float> priorities);

    /**
     * TODO
     * @param encoding
     * @param value
     * @return
     */
    EncodingNegotiatorConfigurer priority(String encoding, Float value);
}
