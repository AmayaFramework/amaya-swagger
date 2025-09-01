package io.github.amayaframework.compress;

import java.util.Collection;
import java.util.Map;

/**
 * TODO
 */
public interface CompressNegotiatorConfigurer {

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
    CompressNegotiatorConfigurer parser(EncodingHeaderParser parser);

    /**
     * TODO
     * @return
     */
    CompressManager manager();

    /**
     * TODO
     * @param manager
     * @return
     */
    CompressNegotiatorConfigurer manager(CompressManager manager);

    /**
     * TODO
     * @return
     */
    Collection<CompressEncoder> encoders();

    /**
     * TODO
     * @param encoder
     * @param priority
     * @return
     */
    CompressNegotiatorConfigurer addEncoder(CompressEncoder encoder, Float priority);

    /**
     * TODO
     * @param encoder
     * @return
     */
    CompressNegotiatorConfigurer addEncoder(CompressEncoder encoder);

    /**
     * TODO
     * @param name
     * @return
     */
    CompressNegotiatorConfigurer removeEncoder(String name);

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
    CompressNegotiatorConfigurer priorities(Map<String, Float> priorities);

    /**
     * TODO
     * @param encoding
     * @param value
     * @return
     */
    CompressNegotiatorConfigurer priority(String encoding, Float value);
}
