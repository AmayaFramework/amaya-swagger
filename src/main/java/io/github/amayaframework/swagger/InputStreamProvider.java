package io.github.amayaframework.swagger;

import java.io.IOException;
import java.io.InputStream;

/**
 * A provider of {@link InputStream} instances for reading
 * OpenAPI specification documents.
 * <p>
 * This functional interface allows different strategies
 * for supplying an input stream, such as reading from a file,
 * classpath resource, network location, or an in-memory buffer.
 */
@FunctionalInterface
public interface InputStreamProvider {

    /**
     * Opens a new {@link InputStream} for reading the document.
     * <p>
     * Each invocation should return a fresh stream positioned
     * at the beginning of the content.
     *
     * @return a new input stream
     * @throws IOException if the stream cannot be opened
     */
    InputStream get() throws IOException;
}
