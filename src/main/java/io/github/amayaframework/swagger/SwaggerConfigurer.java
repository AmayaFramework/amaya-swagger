//package io.github.amayaframework.swagger;
//
//import com.github.romanqed.jfunc.Runnable1;
//import com.github.romanqed.jfunc.Runnable2;
//import io.github.amayaframework.context.HttpContext;
//import io.github.amayaframework.web.WebApplication;
//
//import java.net.URI;
//import java.util.Collection;
//
///**
// * An interface describing an abstract swagger SPA configurator.
// * It can be applied to {@link WebApplication} via
// * {@link io.github.amayaframework.web.WebApplicationBuilder#configureApplication(Runnable1)}
// * or {@link WebApplication#addHandler(Runnable2)}.
// */
//public interface SwaggerConfigurer extends Runnable1<WebApplication> {
//
//    /**
//     * Gets the {@link MimeTyper} instance.
//     *
//     * @return the {@link MimeTyper} instance
//     */
//    MimeTyper getMimeTyper();
//
//    /**
//     * Sets the {@link MimeTyper} instance.
//     *
//     * @param typer the specified {@link MimeTyper} instance, must be non-null
//     */
//    void setMimeTyper(MimeTyper typer);
//
//    /**
//     * Gets the root of swagger SPA static.
//     *
//     * @return the root of swagger static
//     */
//    URI getRoot();
//
//    /**
//     * Sets the root of swagger SPA static.
//     *
//     * @param root the specified uri, must be non-absolute and non-null
//     */
//    void setRoot(URI root);
//
//    /**
//     * Gets the location of swagger web page.
//     *
//     * @return the location of swagger web page
//     */
//    URI getSwaggerURI();
//
//    /**
//     * Sets the location of swagger web page.
//     *
//     * @param swagger the specified uri, must be non-absolute and non-null
//     */
//    void setSwaggerURI(URI swagger);
//
//    /**
//     * Adds the {@link OpenAPIDocument} to swagger application.
//     *
//     * @param document the specified {@link OpenAPIDocument} instance to be added, must be non-null
//     */
//    void addDocument(OpenAPIDocument document);
//
//    /**
//     * Removes the {@link OpenAPIDocument} from swagger application.
//     *
//     * @param document the specified {@link OpenAPIDocument} instance to be removed, may be null
//     */
//    void removeDocument(OpenAPIDocument document);
//
//    /**
//     * Removes the {@link OpenAPIDocument} by given path from swagger application.
//     *
//     * @param path the specified document path, may be null
//     */
//    void removeDocument(URI path);
//
//    /**
//     * Gets all documents added to this swagger application.
//     *
//     * @return all documents added to this swagger application
//     */
//    Collection<OpenAPIDocument> getDocuments();
//
//    /**
//     * Creates http context handler and resets inner state of this configurer.
//     *
//     * @return the handler instance
//     */
//    Runnable2<HttpContext, Runnable1<HttpContext>> createHandler();
//
//    /**
//     * Adds to application context handler and resets inner state of this configurer.
//     *
//     * @param application the specified web application to be configured
//     * @throws Throwable if any problems occurred
//     */
//    @Override
//    void run(WebApplication application) throws Throwable;
//}
