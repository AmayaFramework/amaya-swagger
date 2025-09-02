/**
 * Provides integration of Swagger UI and OpenAPI document serving
 * into an Amaya web application.
 *
 * <p>This module includes:
 * <ul>
 *   <li>{@link io.github.amayaframework.swagger.SwaggerTaskBuilder} –
 *   a builder for creating Swagger tasks that serve the UI and documents,</li>
 *   <li>{@link io.github.amayaframework.swagger.SwaggerApplicationConfigurer} –
 *   application-level configurer for registering Swagger in
 *   {@link io.github.amayaframework.web.WebApplication},</li>
 *   <li>{@link io.github.amayaframework.swagger.SwaggerOptions} –
 *   option keys for configuring Swagger via {@link io.github.amayaframework.options.OptionSet},</li>
 *   <li>{@link io.github.amayaframework.swagger.OpenApiSource} and related
 *   classes for representing OpenAPI document sources,</li>
 *   <li>support for content negotiation and compression using
 *   {@link io.github.amayaframework.compress.CompressNegotiator}.</li>
 * </ul>
 */
module amayaframework.swagger {
    // TODO REMOVE dev deps
    requires amayaframework.core;
    requires amayaframework.tomcat;
    requires amayaframework.swaggerui;
    // Imports
    // Basic dependencies
    requires com.github.romanqed.jtype;
    requires com.github.romanqed.jsync;
    // Amaya modules
    requires amayaframework.options;
    requires amayaframework.web;
    requires amayaframework.openui;
    requires amayaframework.compress;
    requires amayaframework.environment;
    // Exports
    exports io.github.amayaframework.swagger;
}
