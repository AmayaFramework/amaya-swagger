package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeType;

public enum OpenApiFormat {
    YAML(MimeTypes.OPEN_API_YAML),
    JSON(MimeTypes.OPEN_API_JSON);

    final MimeType type;

    OpenApiFormat(MimeType type) {
        this.type = type;
    }

    public MimeType type() {
        return type;
    }
}
