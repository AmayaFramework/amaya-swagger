package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeType;

/**
 * TODO
 */
public final class SwaggerMimeTypes {
    private SwaggerMimeTypes() {
    }

    /**
     * TODO
     */
    public static final MimeType OPEN_API_YAML = new MimeType("application", "openapi+yaml", true);

    /**
     * TODO
     */
    public static final MimeType OPEN_API_JSON = new MimeType("application", "openapi+json", true);
}
