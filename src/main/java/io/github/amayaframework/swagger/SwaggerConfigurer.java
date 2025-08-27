package io.github.amayaframework.swagger;

import io.github.amayaframework.application.Resettable;
import io.github.amayaframework.compress.EncodingNegotiator;
import io.github.amayaframework.compress.EncodingNegotiatorConfigurer;
import io.github.amayaframework.openui.OpenUiFactory;

import java.net.URI;
import java.util.Collection;
import java.util.function.Consumer;

public interface SwaggerConfigurer extends Resettable {

    EncodingNegotiatorConfigurer negotiatorConfigurer(); // если неготиатора не будет, будет использован конфигуратор

    SwaggerConfigurer configureNegotiator(Consumer<EncodingNegotiatorConfigurer> action);

    EncodingNegotiator negotiator(); // если задан, то конфигуратор игнорируется

    SwaggerConfigurer negotiator(EncodingNegotiator negotiator);

    OpenUiFactory uiFactory();

    SwaggerConfigurer uiFactory(OpenUiFactory factory);

    URI root();

    SwaggerConfigurer root(URI root);

    Collection<OpenApiSource> documents();

    SwaggerConfigurer addDocument(OpenApiSource source);

    SwaggerConfigurer addDocuments(OpenApiSource... sources);

    SwaggerConfigurer addDocuments(Iterable<OpenApiSource> sources);

    Collection<OpenApiSource> exposedDocuments();

    SwaggerConfigurer exposeDocument(OpenApiSource source, String charset);

    default SwaggerConfigurer exposeDocument(OpenApiSource source) {
        return exposeDocument(source, null);
    }
}
