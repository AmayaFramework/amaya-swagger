package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.web.WebApplication;

import java.net.URI;
import java.util.Collection;

public interface SwaggerConfigurer extends Runnable1<WebApplication> {

    MimeTyper getMimeTyper();

    void setMimeTyper(MimeTyper typer);

    URI getRoot();

    void setRoot(URI root);

    URI getSwaggerURI();

    void setSwaggerURI(URI swagger);

    void addDocument(OpenAPIDocument document);

    void removeDocument(OpenAPIDocument document);

    void removeDocument(URI path);

    Collection<OpenAPIDocument> getDocuments();

    Runnable2<HttpContext, Runnable1<HttpContext>> createHandler();

    @Override
    void run(WebApplication application) throws Throwable;
}
