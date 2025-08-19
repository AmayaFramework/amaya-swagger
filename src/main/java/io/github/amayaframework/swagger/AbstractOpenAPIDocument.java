//package io.github.amayaframework.swagger;
//
//import java.net.URI;
//
///**
// * The skeletal implementation of the {@link OpenAPIDocument}.
// */
//public abstract class AbstractOpenAPIDocument implements OpenAPIDocument {
//    protected final URI path;
//    protected final String title;
//
//    /**
//     * Constructs an {@link AbstractOpenAPIDocument} instance with given path and title.
//     *
//     * @param path  the specified path, must be non-null
//     * @param title the specified title, may be null
//     */
//    protected AbstractOpenAPIDocument(URI path, String title) {
//        this.title = title;
//        this.path = path;
//    }
//
//    @Override
//    public String getTitle() {
//        return title;
//    }
//
//    @Override
//    public URI getPath() {
//        return path;
//    }
//}
