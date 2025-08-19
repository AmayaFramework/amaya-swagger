//package io.github.amayaframework.swagger;
//
//import com.github.romanqed.jfunc.Function0;
//import com.github.romanqed.jfunc.Runnable1;
//import com.github.romanqed.jfunc.Runnable2;
//import io.github.amayaframework.context.HttpContext;
//import io.github.amayaframework.openui.ApiEntry;
//import io.github.amayaframework.openui.OpenUI;
//import io.github.amayaframework.openui.OpenUIFactory;
//import io.github.amayaframework.web.WebApplication;
//
//import java.io.InputStream;
//import java.net.URI;
//import java.util.*;
//
//final class MapSwaggerConfigurer implements SwaggerConfigurer {
//    // Immutable fields
//    private final OpenUIFactory factory;
//    private final MimeTyper defaultTyper;
//    private final URI defaultRoot;
//    // Configurable fields
//    private MimeTyper typer;
//    private URI root;
//    private URI swagger;
//    private Map<URI, OpenAPIDocument> documents;
//
//    MapSwaggerConfigurer(OpenUIFactory factory, MimeTyper typer, URI root) {
//        this.factory = factory;
//        this.defaultTyper = typer;
//        this.defaultRoot = root;
//        this.reset();
//    }
//
//    private void reset() {
//        this.typer = defaultTyper;
//        this.root = defaultRoot;
//        this.swagger = defaultRoot;
//        this.documents = null;
//    }
//
//    @Override
//    public MimeTyper getMimeTyper() {
//        return typer;
//    }
//
//    @Override
//    public void setMimeTyper(MimeTyper typer) {
//        this.typer = Objects.requireNonNull(typer);
//    }
//
//    @Override
//    public URI getRoot() {
//        return root;
//    }
//
//    @Override
//    public void setRoot(URI root) {
//        Objects.requireNonNull(root);
//        if (root.isAbsolute()) {
//            throw new IllegalArgumentException("Swagger static root uri cannot be absolute");
//        }
//        this.root = root;
//    }
//
//    @Override
//    public URI getSwaggerURI() {
//        return swagger;
//    }
//
//    @Override
//    public void setSwaggerURI(URI swagger) {
//        Objects.requireNonNull(swagger);
//        if (swagger.isAbsolute()) {
//            throw new IllegalArgumentException("Swagger page uri cannot be absolute");
//        }
//        this.swagger = swagger;
//    }
//
//    @Override
//    public void addDocument(OpenAPIDocument document) {
//        Objects.requireNonNull(document);
//        if (documents == null) {
//            documents = new HashMap<>();
//        }
//        documents.put(document.getPath(), document);
//    }
//
//    @Override
//    public void removeDocument(OpenAPIDocument document) {
//        if (document == null) {
//            return;
//        }
//        removeDocument(document.getPath());
//    }
//
//    @Override
//    public void removeDocument(URI path) {
//        if (documents == null) {
//            return;
//        }
//        documents.remove(path);
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public Collection<OpenAPIDocument> getDocuments() {
//        if (documents == null) {
//            return Collections.EMPTY_LIST;
//        }
//        return Collections.unmodifiableCollection(documents.values());
//    }
//
//    private OpenUI createSingleUI(URI root, URI uri, String title) {
//        if (title == null) {
//            return factory.create(root, uri);
//        }
//        return factory.create(root, ApiEntry.of(uri, title));
//    }
//
//    private OpenUI createSingleLocalUI(URI root, String rawUri, String title) {
//        var uri = PathUtil.getURI(root, rawUri);
//        return createSingleUI(root, uri, title);
//    }
//
//    private SwaggerHandler createSingleLocalHandler(OpenAPIDocument document, URI root, URI swagger) {
//        var uri = document.getPath().normalize();
//        var raw = PathUtil.normalizeTail(uri.getRawPath()); // seg1/file.ext
//        var ui = createSingleLocalUI(root, raw, document.getTitle());
//        var providers = Map.of(raw, document.getProvider());
//        return new SwaggerHandler(
//                typer,
//                PathUtil.normalize(root),
//                PathUtil.normalize(swagger),
//                ui,
//                providers
//        );
//    }
//
//    @SuppressWarnings("unchecked")
//    private SwaggerHandler createSingleRemoteHandler(OpenAPIDocument document, URI root, URI swagger) {
//        var ui = createSingleUI(root, document.getPath(), document.getTitle());
//        return new SwaggerHandler(
//                typer,
//                PathUtil.normalize(root),
//                PathUtil.normalize(swagger),
//                ui,
//                Collections.EMPTY_MAP
//        );
//    }
//
//    private SwaggerHandler createSingleHandler(OpenAPIDocument document, URI root, URI swagger) {
//        if (document.getPath().isAbsolute()) {
//            return createSingleRemoteHandler(document, root, swagger);
//        }
//        return createSingleLocalHandler(document, root, swagger);
//    }
//
//    private SwaggerHandler create() {
//        // Check if documents is null or empty
//        if (documents == null || documents.isEmpty()) {
//            throw new IllegalStateException("It is impossible to create a swagger without at least one api document");
//        }
//        var root = this.root.normalize();
//        var swagger = this.swagger.normalize();
//        if (documents.size() == 1) {
//            return createSingleHandler(documents.values().iterator().next(), root, swagger);
//        }
//        var entries = new LinkedList<ApiEntry>();
//        var providers = new HashMap<String, Function0<InputStream>>();
//        for (var document : documents.values()) {
//            var uri = document.getPath();
//            var title = document.getTitle();
//            if (uri.isAbsolute()) {
//                var entry = ApiEntry.of(uri, title == null ? uri.getPath() : title);
//                entries.add(entry);
//                continue;
//            }
//            var combined = PathUtil.getURI(root, uri);
//            var entry = ApiEntry.of(combined, title == null ? combined.getPath() : title);
//            entries.add(entry);
//            providers.put(PathUtil.normalizeTail(uri), document.getProvider());
//        }
//        var ui = factory.create(root, entries);
//        return new SwaggerHandler(
//                typer,
//                PathUtil.normalize(root),
//                PathUtil.normalize(swagger),
//                ui,
//                providers
//        );
//    }
//
//    @Override
//    public Runnable2<HttpContext, Runnable1<HttpContext>> createHandler() {
//        try {
//            return create();
//        } catch (Error | RuntimeException e) {
//            throw e;
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        } finally {
//            reset();
//        }
//    }
//
//    @Override
//    public void run(WebApplication application) {
//        try {
//            var handler = create();
//            application.addHandler(handler);
//        } catch (Error | RuntimeException e) {
//            throw e;
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        } finally {
//            reset();
//        }
//    }
//}
