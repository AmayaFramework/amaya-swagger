package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeType;

/**
 * Common {@link MimeType} constants for OpenAPI documents.
 * <p>
 * These MIME types are non-standard but follow the common
 * convention of using {@code application/openapi+json} and
 * {@code application/openapi+yaml}.
 */
public final class SwaggerMimeTypes {
    private SwaggerMimeTypes() {
    }

    /**
     * MIME type for OpenAPI documents in YAML format.
     */
    public static final MimeType OPEN_API_YAML = new MimeType("application", "openapi+yaml", true);
    /**
     * MIME type for OpenAPI documents in JSON format.
     */
    public static final MimeType OPEN_API_JSON = new MimeType("application", "openapi+json", true);
}
