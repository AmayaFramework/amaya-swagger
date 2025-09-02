package io.github.amayaframework.swaggerui;

import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.openui.OpenApiEntry;
import io.github.amayaframework.openui.OpenUi;
import io.github.amayaframework.openui.OpenUiFactory;
import io.github.amayaframework.openui.Part;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Factory implementation of {@link OpenUiFactory} that provides
 * a ready-to-use Swagger UI distribution.
 * <p>
 * This implementation bundles the official <a href="https://github.com/swagger-api/swagger-ui">swagger-api/swagger-ui</a>
 * project and serves its static assets (HTML, CSS, JavaScript, icons).
 * It also generates the dynamic {@code init.js} script that configures
 * the UI with one or more {@link OpenApiEntry} definitions.
 * <p>
 * Typical usage:
 * <pre>{@code
 * OpenUiFactory factory = new SwaggerUiFactory();
 * OpenUi ui = factory.create(URI.create("/openapi.yaml"));
 * }</pre>
 * The returned {@link OpenUi} can then be integrated into a server to expose
 * the Swagger UI for browsing and testing APIs.
 */
public final class SwaggerUiFactory implements OpenUiFactory {
    private static final String UTF_8 = "utf-8";
    private static final String INIT = "init.js";
    private static final Part INDEX = new ResourcePart("index.html", MimeType.HTML, UTF_8);
    private static final Map<String, Part> PARTS = Map.of(
            "favicon-16x16.png", new ResourcePart("favicon-16x16.png", MimeType.PNG),
            "favicon-32x32.png", new ResourcePart("favicon-32x32.png", MimeType.PNG),
            "index.html", INDEX,
            "index.css", new ResourcePart("index.css", MimeType.CSS, UTF_8),
            "oauth2-redirect.html", new ResourcePart("oauth2-redirect.html", MimeType.HTML, UTF_8),
            "swagger-ui.css", new ResourcePart("swagger-ui.css", MimeType.CSS, UTF_8),
            "swagger-ui-bundle.js", new ResourcePart("swagger-ui-bundle.js", MimeType.APPLICATION_JAVASCRIPT, UTF_8),
            "swagger-ui-standalone-preset.js", new ResourcePart("swagger-ui-standalone-preset.js", MimeType.APPLICATION_JAVASCRIPT, UTF_8)
    );

    private static BufferPart createInitPart(String buffer) {
        return new BufferPart(INIT, MimeType.APPLICATION_JAVASCRIPT, buffer.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public OpenUi create() {
        var parts = new HashMap<>(PARTS);
        parts.put(INIT, createInitPart(StringUtil.generateInit()));
        return new SwaggerUi(parts, INDEX);
    }

    @Override
    public OpenUi create(URI uri) {
        Objects.requireNonNull(uri);
        var parts = new HashMap<>(PARTS);
        parts.put(INIT, createInitPart(StringUtil.generateInit(uri)));
        return new SwaggerUi(parts, INDEX);
    }

    @Override
    public OpenUi create(OpenApiEntry entry) {
        Objects.requireNonNull(entry);
        var parts = new HashMap<>(PARTS);
        parts.put(INIT, createInitPart(StringUtil.generateInit(entry)));
        return new SwaggerUi(parts, INDEX);
    }

    @Override
    public OpenUi create(OpenApiEntry... entries) {
        Objects.requireNonNull(entries);
        var parts = new HashMap<>(PARTS);
        parts.put(INIT, createInitPart(StringUtil.generateInit(entries)));
        return new SwaggerUi(parts, INDEX);
    }

    @Override
    public OpenUi create(Iterable<OpenApiEntry> entries) {
        Objects.requireNonNull(entries);
        var parts = new HashMap<>(PARTS);
        parts.put(INIT, createInitPart(StringUtil.generateInit(entries)));
        return new SwaggerUi(parts, INDEX);
    }
}
