//package io.github.amayaframework.swagger;
//
//import com.github.romanqed.jfunc.Function0;
//
//import java.io.InputStream;
//import java.net.URI;
//
/// **
// * The implementation of the {@link OpenAPIDocument} without the {@link InputStream} provider.
// */
//public final class RemoteOpenAPIDocument extends AbstractOpenAPIDocument {
//
//    /**
//     * Constructs a {@link ProvidedOpenAPIDocument} instance with the given document path and document title
//     *
//     * @param path  the specified document path, must be non-null
//     * @param title the specified document title, may be null
//     */
//    public RemoteOpenAPIDocument(URI path, String title) {
//        super(path, title);
//    }
//
//    @Override
//    public Function0<InputStream> getProvider() {
//        return null;
//    }
//}
