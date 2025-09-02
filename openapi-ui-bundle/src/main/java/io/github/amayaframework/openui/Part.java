package io.github.amayaframework.openui;

import io.github.amayaframework.http.MimeType;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a single resource served by {@link OpenUi}, such as an HTML page,
 * JavaScript file, CSS stylesheet, or other Swagger UI asset.
 * Provides access to metadata and the underlying content stream.
 */
public interface Part {

    /**
     * Returns the unique name of this part, typically corresponding to the
     * requested resource path (e.g. {@code "index.html"} or {@code "swagger-ui.css"}).
     *
     * @return the name of the part
     */
    String name();

    /**
     * Returns the MIME type of this part, such as
     * {@link MimeType#HTML} or {@link MimeType#JSON}.
     *
     * @return the MIME type
     */
    MimeType mimeType();

    /**
     * Returns the character set of this part if applicable (e.g. for text-based resources).
     * May be {@code null} if the part is binary or encoding is not defined.
     *
     * @return the charset or {@code null}
     */
    String charset();

    /**
     * Opens a stream to read the content of this part.
     * The caller is responsible for closing the returned stream.
     *
     * @return input stream with the part’s content
     * @throws IOException if the stream cannot be opened
     */
    InputStream inputStream() throws IOException;
}
