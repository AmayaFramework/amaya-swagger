package io.github.amayaframework.swagger;

import com.github.romanqed.jtype.JType;
import io.github.amayaframework.compress.CompressNegotiator;
import io.github.amayaframework.openui.OpenUiFactory;
import io.github.amayaframework.options.Key;

import java.net.URI;

/**
 * TODO
 */
public final class SwaggerOptions {
    private SwaggerOptions() {
    }

    /**
     * TODO
     */
    public static final String SWAGGER_GROUP = "swagger";

    /**
     * TODO
     */
    public static final Key<OpenUiFactory> UI_FACTORY = Key.of("ui_factory", OpenUiFactory.class);

    /**
     * TODO
     */
    public static final Key<URI> ROOT = Key.of("root", URI.class);

    /**
     * TODO
     */
    public static final Key<Iterable<OpenApiSource>> DOCS = Key.of("docs", new JType<>(){});

    /**
     * TODO
     */
    public static final Key<Iterable<OpenApiSource>> EXPOSED_DOCS = Key.of("exposed_docs", new JType<>(){});

    /**
     * TODO
     */
    public static final Key<CompressNegotiator> COMPRESS_NEGOTIATOR = Key.of("cng", CompressNegotiator.class);
}
