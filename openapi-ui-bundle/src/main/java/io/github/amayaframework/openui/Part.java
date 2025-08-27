package io.github.amayaframework.openui;

import io.github.amayaframework.http.MimeType;

import java.io.IOException;
import java.io.InputStream;

/**
 * TODO
 */
public interface Part {

    /**
     * TODO
     * @return
     */
    String name();

    /**
     * TODO
     * @return
     */
    MimeType mimeType();

    /**
     * TODO
     * @return
     */
    String charset();

    /**
     * TODO
     * @return
     * @throws IOException
     */
    InputStream inputStream() throws IOException;
}
