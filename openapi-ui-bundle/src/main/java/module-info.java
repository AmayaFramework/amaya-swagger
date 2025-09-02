/**
 * Provides a server-side abstraction for embedding Swagger UI.
 * <p>
 * The {@code amayaframework.openui} module defines the {@link io.github.amayaframework.openui.OpenUi}
 * API for serving Swagger UI resources and linking them to one or more
 * OpenAPI specification entries represented by {@link io.github.amayaframework.openui.OpenApiEntry}.
 * <p>
 * This module depends on {@code amayaframework.http} for MIME type handling.
 */
module amayaframework.openui {
    // Imports
    requires amayaframework.http;
    // Exports
    exports io.github.amayaframework.openui;
}
