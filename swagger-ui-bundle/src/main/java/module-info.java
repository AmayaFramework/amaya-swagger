/**
 * Provides a concrete {@link io.github.amayaframework.openui.OpenUiFactory}
 * implementation based on the official <a href="https://github.com/swagger-api/swagger-ui">Swagger UI</a>.
 * <p>
 * This module assembles the static assets (HTML, CSS, JavaScript, icons) from Swagger UI
 * and dynamically generates an initialization script that links the UI with one or more
 * {@link io.github.amayaframework.openui.OpenApiEntry} definitions.
 * <p>
 * Depends on {@code amayaframework.openui} for the generic UI abstraction
 * and {@code amayaframework.http} for MIME type handling.
 */
module amayaframework.swaggerui {
    // Imports
    requires amayaframework.openui;
    requires amayaframework.http;
    // Exports
    exports io.github.amayaframework.swaggerui;
}
