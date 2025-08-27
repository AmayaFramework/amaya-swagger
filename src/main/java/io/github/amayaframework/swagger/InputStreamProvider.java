package io.github.amayaframework.swagger;

import java.io.IOException;
import java.io.InputStream;

/**
 * TODO
 */
@FunctionalInterface
public interface InputStreamProvider {

    /**
     * TODO
     * @return
     * @throws IOException
     */
    InputStream get() throws IOException;
}
