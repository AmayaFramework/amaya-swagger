package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeType;

/**
 * TODO
 */
public enum OpenApiFormat {
    YAML(MimeTypes.OPEN_API_YAML),
    JSON(MimeTypes.OPEN_API_JSON);

    final MimeType type;

    OpenApiFormat(MimeType type) {
        this.type = type;
    }

    /**
     * TODO
     * @return
     */
    public MimeType type() {
        return type;
    }
}
