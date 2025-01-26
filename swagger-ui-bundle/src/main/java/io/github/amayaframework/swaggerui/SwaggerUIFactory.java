package io.github.amayaframework.swaggerui;

import io.github.amayaframework.openui.ApiEntry;
import io.github.amayaframework.openui.OpenUI;
import io.github.amayaframework.openui.OpenUIFactory;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * The {@link OpenUIFactory} implementation for swagger-ui from
 * official swagger <a href="https://github.com/swagger-api/swagger-ui">repository</a>.
 */
public final class SwaggerUIFactory implements OpenUIFactory {
    private final Charset charset;

    /**
     * Constructs a {@link OpenUIFactory} instance with given charset that will be used to encode files.
     *
     * @param charset the specified charset
     */
    public SwaggerUIFactory(Charset charset) {
        this.charset = Objects.requireNonNull(charset);
    }

    /**
     * Constructs a {@link OpenUIFactory} instance with UTF-8 charset for files.
     */
    public SwaggerUIFactory() {
        this.charset = StandardCharsets.UTF_8;
    }

    @Override
    public OpenUI create(URI root, URI uri) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(uri);
        try {
            var index = Util.getIndex();
            index = FormatUtil.setRoot(index, root);
            index = FormatUtil.setUrl(index, uri.toString());
            var buffer = index.getBytes(charset);
            return new SwaggerUI(root, buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OpenUI create(URI root, ApiEntry entry) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(entry);
        try {
            var index = Util.getIndex();
            index = FormatUtil.setRoot(index, root);
            index = FormatUtil.setUrlEntry(index, entry);
            var buffer = index.getBytes(charset);
            return new SwaggerUI(root, buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OpenUI create(URI root, ApiEntry... entries) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(entries);
        try {
            var index = Util.getIndex();
            index = FormatUtil.setRoot(index, root);
            index = FormatUtil.setUrls(index, entries);
            var buffer = index.getBytes(charset);
            return new SwaggerUI(root, buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OpenUI create(URI root, Iterable<ApiEntry> entries) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(entries);
        try {
            var index = Util.getIndex();
            index = FormatUtil.setRoot(index, root);
            index = FormatUtil.setUrls(index, entries);
            var buffer = index.getBytes(charset);
            return new SwaggerUI(root, buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
