//package io.github.amayaframework.swagger;
//
//import com.github.romanqed.jfunc.Function0;
//
//import java.io.InputStream;
//import java.net.URI;
//
///**
// * The implementation of the {@link OpenAPIDocument} with the {@link InputStream} provider.
// */
//public final class ProvidedOpenAPIDocument extends AbstractOpenAPIDocument {
//    private final Function0<InputStream> provider;
//
//    /**
//     * Constructs a {@link ProvidedOpenAPIDocument} instance with the given document path, document title
//     * and {@link InputStream} provider.
//     *
//     * @param path     the specified document path, must be non-null
//     * @param title    the specified document title, may be null
//     * @param provider the specified document provider
//     */
//    public ProvidedOpenAPIDocument(URI path, String title, Function0<InputStream> provider) {
//        super(path, title);
//        this.provider = provider;
//    }
//
//    @Override
//    public Function0<InputStream> getProvider() {
//        return provider;
//    }
//}
