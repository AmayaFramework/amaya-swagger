package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeType;

/**
 * Supported OpenAPI document formats.
 * <p>
 * Each format is associated with a specific {@link MimeType}
 * for use in HTTP responses.
 */
public enum OpenApiFormat {
    /**
     * OpenAPI document in YAML format.
     */
    YAML(SwaggerMimeTypes.OPEN_API_YAML),

    /**
     * OpenAPI document in JSON format.
     */
    JSON(SwaggerMimeTypes.OPEN_API_JSON);

    final MimeType type;

    OpenApiFormat(MimeType type) {
        this.type = type;
    }

    /**
     * Returns the {@link MimeType} associated with this format.
     *
     * @return the MIME type
     */
    public MimeType type() {
        return type;
    }
}
