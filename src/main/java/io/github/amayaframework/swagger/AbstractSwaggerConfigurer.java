package io.github.amayaframework.swagger;

import io.github.amayaframework.compress.CompressNegotiator;
import io.github.amayaframework.openui.OpenUiFactory;

import java.net.URI;
import java.util.*;

/**
 * TODO
 *
 * @param <C>
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
     * TODO
     */
    protected void ensureDocuments() {
        if (documents == null) {
            documents = new LinkedList<>();
            documentsView = Collections.unmodifiableCollection(documents);
        }
    }

    /**
     * TODO
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
            throw new IllegalArgumentException("TODO MSG: swagger root не может быть абсолютным");
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
        Objects.requireNonNull(source);
        ensureDocuments();
        documents.add(source);
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C addDocuments(OpenApiSource... sources) {
        Objects.requireNonNull(sources);
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
        Objects.requireNonNull(sources);
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
        Objects.requireNonNull(source);
        Objects.requireNonNull(source.format(), "TODO MSG");
        Objects.requireNonNull(source.provider(), "TODO MSG");
        ensureExposed();
        exposed.add(source);
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C exposeDocuments(OpenApiSource... sources) {
        // noinspection DuplicatedCode
        Objects.requireNonNull(sources);
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
        // noinspection DuplicatedCode
        Objects.requireNonNull(sources);
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
