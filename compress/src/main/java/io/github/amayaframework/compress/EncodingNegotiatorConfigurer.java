package io.github.amayaframework.compress;

import java.util.Collection;
import java.util.Map;

public interface EncodingNegotiatorConfigurer {

    void reset();

    EncodingHeaderParser parser();

    EncodingNegotiatorConfigurer parser(EncodingHeaderParser parser);

    EncodingManager manager();

    EncodingNegotiatorConfigurer manager(EncodingManager manager);

    Collection<Encoder> encoders();

    EncodingNegotiatorConfigurer addEncoder(Encoder encoder, Float priority);

    EncodingNegotiatorConfigurer addEncoder(Encoder encoder);

    EncodingNegotiatorConfigurer removeEncoder(String name);

    Map<String, Float> priorities();

    EncodingNegotiatorConfigurer priorities(Map<String, Float> priorities);

    EncodingNegotiatorConfigurer priority(String encoding, Float value);
}
