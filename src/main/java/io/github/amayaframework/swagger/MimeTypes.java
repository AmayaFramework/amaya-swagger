package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeType;

/**
 * TODO
 */
public final class MimeTypes {
    public static final MimeType OPEN_API_YAML = new MimeType("application", "openapi+yaml", true);
    public static final MimeType OPEN_API_JSON = new MimeType("application", "openapi+json", true);

    private MimeTypes() {
    }
}
