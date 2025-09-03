package io.github.amayaframework.swagger;

import com.github.romanqed.jtype.JType;
import io.github.amayaframework.compress.CompressNegotiator;
import io.github.amayaframework.compress.CompressNegotiatorConfigurer;
import io.github.amayaframework.openui.OpenUiFactory;
import io.github.amayaframework.options.Key;

import java.net.URI;
import java.util.function.Consumer;

/**
 * Defines option keys for configuring the Swagger module.
 * <p>
 * These keys can be used with the framework's options API to
 * supply custom settings for Swagger UI, compression, and
 * OpenAPI document handling.
 */
public final class SwaggerOptions {
    private SwaggerOptions() {
    }

    /**
     * Name of the Swagger options group.
     */
    public static final String SWAGGER_GROUP = "swagger";

    /**
     * Key for providing a custom {@link OpenUiFactory}.
     * <p>
     * If not set, {@link SwaggerTaskBuilder} will require
     * an explicit factory to be configured.
     */
    public static final Key<OpenUiFactory> UI_FACTORY = Key.of("ui_factory", OpenUiFactory.class);

    /**
     * Key for configuring the root {@link URI} under which
     * Swagger UI and documents are served.
     */
    public static final Key<URI> ROOT = Key.of("root", URI.class);

    /**
     * Key for registering OpenAPI documents that should be
     * displayed in Swagger UI, but not served by the module.
     * <p>
     * Equivalent to {@code addDocuments(...)}.
     */
    public static final Key<Iterable<OpenApiSource>> DOCS = Key.of("docs", new JType<>(){});

    /**
     * Key for registering OpenAPI documents that should be
     * both displayed in Swagger UI and served by the module.
     * <p>
     * Equivalent to {@code exposeDocuments(...)}.
     */
    public static final Key<Iterable<OpenApiSource>> EXPOSED_DOCS = Key.of("exposed_docs", new JType<>(){});

    /**
     * Key for providing a fully configured {@link CompressNegotiator}.
     * <p>
     * If present, {@link #COMPRESS_NEGOTIATOR_CONFIGURER} is ignored.
     */
    public static final Key<CompressNegotiator> COMPRESS_NEGOTIATOR = Key.of(
            "compress_negotiator",
            CompressNegotiator.class
    );

    /**
     * Key for supplying a {@link Consumer} that customizes
     * a {@link CompressNegotiatorConfigurer}.
     * <p>
     * Used only if {@link #COMPRESS_NEGOTIATOR} is not provided.
     */
    public static final Key<Consumer<CompressNegotiatorConfigurer>> COMPRESS_NEGOTIATOR_CONFIGURER = Key.of(
            "compress_negotiator_cfg",
            new JType<>(){}
    );
}
