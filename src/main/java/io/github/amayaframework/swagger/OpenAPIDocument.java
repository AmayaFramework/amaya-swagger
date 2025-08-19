//package io.github.amayaframework.swagger;
//
//import com.github.romanqed.jfunc.Function0;
//
//import java.io.InputStream;
//import java.net.URI;
//
///**
// * An interface describing an abstract open api document entry.
// */
//public interface OpenAPIDocument {
//
//    /**
//     * Gets document title for rendering in swagger-ui.
//     *
//     * @return document title
//     */
//    String getTitle();
//
//    /**
//     * Gets absolute or local path to this document. If path is local, it will be resolved with static root.
//     * For example, if path is "/api.json" and static root is "/swagger", it will be "/swagger/api.json".
//     *
//     * @return path to this document
//     */
//    URI getPath();
//
//    /**
//     * Gets input stream provider for this document.
//     *
//     * @return input stream provider
//     */
//    Function0<InputStream> getProvider();
//}
