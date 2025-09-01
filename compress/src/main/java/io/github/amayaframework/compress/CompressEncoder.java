package io.github.amayaframework.compress;

import java.io.IOException;
import java.io.OutputStream;

/**
 * TODO
 */
public interface CompressEncoder {

    /**
     * TODO
     * @return
     */
    String name();

    /**
     * TODO
     * @param stream
     * @return
     * @throws IOException
     */
    OutputStream encode(OutputStream stream) throws IOException;
}
