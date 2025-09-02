package io.github.amayaframework.swagger;

import io.github.amayaframework.application.Resettable;
import io.github.amayaframework.compress.CompressNegotiator;
import io.github.amayaframework.compress.CompressNegotiatorConfigurer;
import io.github.amayaframework.openui.OpenUiFactory;

import java.net.URI;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Configuration contract for Swagger integration.
 * <p>
 * Provides methods for customizing:
 * <ul>
 *   <li>Compression negotiation ({@link CompressNegotiator} or its configurer).</li>
 *   <li>UI factory ({@link OpenUiFactory}) for serving Swagger UI.</li>
 *   <li>Root URI under which Swagger is exposed.</li>
 *   <li>OpenAPI documents visible in Swagger UI and/or directly served by the module.</li>
 * </ul>
 * <p>
 * Distinction between <b>add</b> and <b>expose</b>:
 * <ul>
 *   <li>{@code add*} – registers a document only for inclusion in Swagger UI.
 *       The document itself must still be served by the application.</li>
 *   <li>{@code expose*} – registers a document for inclusion in Swagger UI
 *       <em>and</em> configures the module to serve the document itself.</li>
 * </ul>
 */
public interface SwaggerConfigurer extends Resettable {

    /**
     * Returns the configurer for building a {@link CompressNegotiator}.
     * <p>
     * Used if no negotiator is explicitly provided.
     *
     * @return the negotiator configurer
     */
    CompressNegotiatorConfigurer negotiatorConfigurer();

    /**
     * Applies a customization to the negotiator configurer.
     * <p>
     * Has effect only if no explicit {@link CompressNegotiator} is set.
     *
     * @param action the customization action
     * @return this configurer
     */
    SwaggerConfigurer configureNegotiator(Consumer<CompressNegotiatorConfigurer> action);

    /**
     * Returns the explicitly configured {@link CompressNegotiator}, if any.
     * <p>
     * If present, the {@link #negotiatorConfigurer()} is ignored.
     *
     * @return the negotiator, or {@code null} if not set
     */
    CompressNegotiator negotiator();

    /**
     * Sets a custom {@link CompressNegotiator}.
     * <p>
     * If set, {@link #negotiatorConfigurer()} is ignored.
     *
     * @param negotiator the negotiator to use
     * @return this configurer
     */
    SwaggerConfigurer negotiator(CompressNegotiator negotiator);

    /**
     * Returns the {@link OpenUiFactory} used to create Swagger UI instances.
     *
     * @return the UI factory
     */
    OpenUiFactory uiFactory();

    /**
     * Sets a custom {@link OpenUiFactory}.
     *
     * @param factory the UI factory to use
     * @return this configurer
     */
    SwaggerConfigurer uiFactory(OpenUiFactory factory);

    /**
     * Returns the root URI under which Swagger UI and documents are served.
     *
     * @return the root URI
     */
    URI root();

    /**
     * Sets the root URI under which Swagger UI and documents are served.
     *
     * @param root the root URI
     * @return this configurer
     */
    SwaggerConfigurer root(URI root);

    /**
     * Sets the root path (relative URI) where Swagger UI will be served.
     * <p>
     * This is a convenience overload that accepts a {@link String}
     * and converts it into a {@link URI}.
     * <p>
     * Example:
     * <pre>{@code
     *   configurer.root("/swagger");
     * }</pre>
     *
     * @param root the root path as a string (must not be absolute, e.g. "/swagger")
     * @return this configurer for chaining
     * @throws IllegalArgumentException if the given root string represents an absolute URI
     */
    default SwaggerConfigurer root(String root) {
        return root(URI.create(root));
    }

    /**
     * Returns all documents registered for Swagger UI (added via
     * {@link #addDocument(OpenApiSource)} or {@link #exposeDocument(OpenApiSource)}).
     *
     * @return the registered documents
     */
    Collection<OpenApiSource> documents();

    /**
     * Adds a new {@link OpenApiSource} for inclusion in Swagger UI.
     * <p>
     * The document will be visible in the UI, but the module will not serve it.
     * The application is responsible for exposing the document at the given URI.
     *
     * @param source the source to add
     * @return this configurer
     */
    SwaggerConfigurer addDocument(OpenApiSource source);

    /**
     * Adds multiple {@link OpenApiSource} documents to the configuration.
     *
     * @param sources the sources to add
     * @return this configurer
     */
    SwaggerConfigurer addDocuments(OpenApiSource... sources);

    /**
     * Adds multiple {@link OpenApiSource} documents to the configuration.
     *
     * @param sources the sources to add
     * @return this configurer
     */
    SwaggerConfigurer addDocuments(Iterable<OpenApiSource> sources);

    /**
     * Returns the collection of documents marked for exposure.
     * <p>
     * These documents are visible in Swagger UI <em>and</em> are
     * served directly by the module.
     *
     * @return the exposed documents
     */
    Collection<OpenApiSource> exposedDocuments();

    /**
     * Exposes a document for Swagger UI and configures the module
     * to serve it directly.
     *
     * @param source the document source
     * @return this configurer
     */
    SwaggerConfigurer exposeDocument(OpenApiSource source);

    /**
     * Exposes multiple documents for Swagger UI and configures the module
     * to serve them directly.
     *
     * @param sources the documents to expose
     * @return this configurer
     */
    SwaggerConfigurer exposeDocuments(OpenApiSource... sources);

    /**
     * Exposes multiple documents for Swagger UI and configures the module
     * to serve them directly.
     *
     * @param sources the documents to expose
     * @return this configurer
     */
    SwaggerConfigurer exposeDocuments(Iterable<OpenApiSource> sources);
}
