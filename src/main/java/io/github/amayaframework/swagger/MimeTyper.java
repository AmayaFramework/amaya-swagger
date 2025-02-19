package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeData;

/**
 *
 */
public interface MimeTyper {

    /**
     *
     * @param extension
     * @return
     */
    MimeData get(String extension);
}
