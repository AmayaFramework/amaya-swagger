package io.github.amayaframework.compress;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Defines an encoder for applying a specific content-encoding
 * (such as <em>gzip</em> or <em>deflate</em>) to an {@link OutputStream}.
 * <p>
 * Implementations wrap the given stream and return an encoded variant
 * that performs compression transparently during writings.
 */
public interface CompressEncoder {

    /**
     * Returns the canonical name of the encoding supported by this encoder,
     * such as {@code "gzip"} or {@code "deflate"}.
     *
     * @return the encoding name
     */
    String name();

    /**
     * Wraps the given output stream with an encoding layer.
     * The caller must close the returned stream to ensure
     * all buffered data is flushed and finalized.
     *
     * @param stream the underlying stream to encode
     * @return an encoded output stream
     * @throws IOException if the stream cannot be wrapped
     */
    OutputStream encode(OutputStream stream) throws IOException;
}
