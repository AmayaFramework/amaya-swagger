package io.github.amayaframework.swagger;

import io.github.amayaframework.application.Resettable;
import io.github.amayaframework.compress.EncodingNegotiator;
import io.github.amayaframework.compress.EncodingNegotiatorConfigurer;
import io.github.amayaframework.openui.OpenUiFactory;

import java.net.URI;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * TODO
 */
public interface SwaggerConfigurer extends Resettable {

    /**
     * TODO
     * @return
     */
    EncodingNegotiatorConfigurer negotiatorConfigurer(); // если неготиатора не будет, будет использован конфигуратор

    /**
     * TODO
     * @param action
     * @return
     */
    SwaggerConfigurer configureNegotiator(Consumer<EncodingNegotiatorConfigurer> action);

    /**
     * TODO
     * @return
     */
    EncodingNegotiator negotiator(); // если задан, то конфигуратор игнорируется

    /**
     * TODO
     * @param negotiator
     * @return
     */
    SwaggerConfigurer negotiator(EncodingNegotiator negotiator);

    /**
     * TODO
     * @return
     */
    OpenUiFactory uiFactory();

    /**
     * TODO
     * @param factory
     * @return
     */
    SwaggerConfigurer uiFactory(OpenUiFactory factory);

    /**
     * TODO
     * @return
     */
    URI root();

    /**
     * TODO
     * @param root
     * @return
     */
    SwaggerConfigurer root(URI root);

    /**
     * TODO
     * @return
     */
    Collection<OpenApiSource> documents();

    /**
     * TODO
     * @param source
     * @return
     */
    SwaggerConfigurer addDocument(OpenApiSource source);

    /**
     * TODO
     * @param sources
     * @return
     */
    SwaggerConfigurer addDocuments(OpenApiSource... sources);

    /**
     * TODO
     * @param sources
     * @return
     */
    SwaggerConfigurer addDocuments(Iterable<OpenApiSource> sources);

    /**
     * TODO
     * @return
     */
    Collection<OpenApiSource> exposedDocuments();

    /**
     * TODO
     * @param source
     * @param charset
     * @return
     */
    SwaggerConfigurer exposeDocument(OpenApiSource source, String charset);

    /**
     * TODO
     * @param source
     * @return
     */
    default SwaggerConfigurer exposeDocument(OpenApiSource source) {
        return exposeDocument(source, null);
    }
}
