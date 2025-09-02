package io.github.amayaframework.swagger;

import io.github.amayaframework.compress.CompressNegotiator;
import io.github.amayaframework.openui.OpenUiFactory;

import java.net.URI;
import java.util.*;

/**
 * Base implementation of {@link SwaggerConfigurer}.
 * <p>
 * Provides storage and default handling for negotiator, UI factory,
 * root URI, and collections of OpenAPI documents.
 * <p>
 * Subclasses may extend this class to implement custom configuration logic.
 *
 * @param <C> the self type for fluent method chaining
 */
public abstract class AbstractSwaggerConfigurer<C extends SwaggerConfigurer> implements SwaggerConfigurer {
    protected CompressNegotiator negotiator;
    protected OpenUiFactory uiFactory;
    protected URI root;
    protected List<OpenApiSource> documents;
    protected Collection<OpenApiSource> documentsView;
    protected List<OpenApiSource> exposed;
    protected Collection<OpenApiSource> exposedView;

    /**
     * Ensures that the documents collection is initialized.
     */
    protected void ensureDocuments() {
        if (documents == null) {
            documents = new LinkedList<>();
            documentsView = Collections.unmodifiableCollection(documents);
        }
    }

    /**
     * Ensures that the exposed documents collection is initialized.
     */
    protected void ensureExposed() {
        if (exposed == null) {
            exposed = new LinkedList<>();
            exposedView = Collections.unmodifiableCollection(exposed);
        }
    }

    @Override
    public void reset() {
        negotiator = null;
        uiFactory = null;
        root = null;
        documents = null;
        documentsView = null;
        exposed = null;
        exposedView = null;
    }

    @Override
    public CompressNegotiator negotiator() {
        return negotiator;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C negotiator(CompressNegotiator negotiator) {
        this.negotiator = negotiator;
        return (C) this;
    }

    @Override
    public OpenUiFactory uiFactory() {
        return uiFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C uiFactory(OpenUiFactory factory) {
        this.uiFactory = factory;
        return (C) this;
    }

    @Override
    public URI root() {
        return root;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C root(URI root) {
        if (root == null) {
            this.root = null;
            return (C) this;
        }
        if (root.isAbsolute()) {
            throw new IllegalArgumentException("Swagger root must not be an absolute URI: " + root);
        }
        this.root = root;
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<OpenApiSource> documents() {
        return documentsView == null ? Collections.EMPTY_LIST : documentsView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C addDocument(OpenApiSource source) {
        Objects.requireNonNull(source, "OpenApiSource must not be null");
        ensureDocuments();
        documents.add(source);
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C addDocuments(OpenApiSource... sources) {
        Objects.requireNonNull(sources, "Sources array must not be null");
        ensureDocuments();
        for (var source : sources) {
            if (source == null) {
                continue;
            }
            documents.add(source);
        }
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C addDocuments(Iterable<OpenApiSource> sources) {
        Objects.requireNonNull(sources, "Sources iterable must not be null");
        ensureDocuments();
        for (var source : sources) {
            if (source == null) {
                continue;
            }
            documents.add(source);
        }
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<OpenApiSource> exposedDocuments() {
        return exposedView == null ? Collections.EMPTY_LIST : exposedView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C exposeDocument(OpenApiSource source) {
        Objects.requireNonNull(source, "OpenApiSource must not be null");
        Objects.requireNonNull(source.format(), "Exposed OpenApiSource must have a non-null format");
        Objects.requireNonNull(source.provider(), "Exposed OpenApiSource must have a non-null provider");
        ensureExposed();
        exposed.add(source);
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C exposeDocuments(OpenApiSource... sources) {
        Objects.requireNonNull(sources, "Sources array must not be null");
        // noinspection DuplicatedCode
        ensureExposed();
        for (var source : sources) {
            if (source == null || source.format() == null || source.provider() == null) {
                continue;
            }
            exposed.add(source);
        }
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C exposeDocuments(Iterable<OpenApiSource> sources) {
        Objects.requireNonNull(sources, "Sources iterable must not be null");
        // noinspection DuplicatedCode
        ensureExposed();
        for (var source : sources) {
            if (source == null || source.format() == null || source.provider() == null) {
                continue;
            }
            exposed.add(source);
        }
        return (C) this;
    }
}
