package io.github.amayaframework.compress;

/**
 * Performs content-encoding negotiation based on the
 * {@code Accept-Encoding} header provided by the client.
 * <p>
 * A negotiator selects the most appropriate {@link CompressEncoder}
 * available from the server’s registry.
 */
@FunctionalInterface
public interface CompressNegotiator {

    /**
     * Selects the best encoder for the given {@code Accept-Encoding} header.
     *
     * @param header the header value
     * @return the negotiated encoder, or {@code null} if none match
     */
    CompressEncoder negotiate(String header);
}
