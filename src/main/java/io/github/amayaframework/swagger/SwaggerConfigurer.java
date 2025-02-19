package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.web.WebApplication;

import java.net.URI;
import java.util.Collection;

/**
 *
 */
public interface SwaggerConfigurer extends Runnable1<WebApplication> {

    /**
     *
     * @return
     */
    MimeTyper getMimeTyper();

    /**
     *
     * @param typer
     */
    void setMimeTyper(MimeTyper typer);

    /**
     *
     * @return
     */
    URI getRoot();

    /**
     *
     * @param root
     */
    void setRoot(URI root);

    /**
     *
     * @return
     */
    URI getSwaggerURI();

    /**
     *
     * @param swagger
     */
    void setSwaggerURI(URI swagger);

    /**
     *
     * @param document
     */
    void addDocument(OpenAPIDocument document);

    /**
     *
     * @param document
     */
    void removeDocument(OpenAPIDocument document);

    /**
     *
     * @param path
     */
    void removeDocument(URI path);

    /**
     *
     * @return
     */
    Collection<OpenAPIDocument> getDocuments();

    /**
     *
     * @return
     */
    Runnable2<HttpContext, Runnable1<HttpContext>> createHandler();

    /**
     *
     * @param application
     * @throws Throwable
     */
    @Override
    void run(WebApplication application) throws Throwable;
}
