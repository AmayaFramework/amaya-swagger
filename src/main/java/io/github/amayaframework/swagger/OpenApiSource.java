package io.github.amayaframework.swagger;

import java.net.URI;

/**
 * TODO
 */
public interface OpenApiSource {

    /**
     * TODO
     *
     * @return
     */
    URI uri();

    /**
     * TODO
     *
     * @return
     */
    String name();

    /**
     * TODO
     *
     * @return
     */
    OpenApiFormat format();

    /**
     * TODO
     *
     * @return
     */
    String charset();

    /**
     * TODO
     *
     * @return
     */
    InputStreamProvider provider();
}
