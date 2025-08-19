//package io.github.amayaframework.swagger;
//
//import com.github.romanqed.jfunc.Function0;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardOpenOption;
//
///**
// * The utility class containing methods that allow to create various implementations of {@link OpenAPIDocument}.
// */
//public final class Documents {
//    private Documents() {
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given url to outer resource and document name.
//     *
//     * @param url  the specified document url, must be non-null
//     * @param name the specified document name, may be null
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(URL url, String name) {
//        try {
//            return new RemoteOpenAPIDocument(url.toURI(), name);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given url to outer resource.
//     *
//     * @param url the specified document url, must be non-null
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(URL url) {
//        return of(url, null);
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given {@link Path} and document name.
//     *
//     * @param path the specified {@link Path} to document file
//     * @param name the specified document name, may be null
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(Path path, String name) {
//        if (Files.notExists(path) || Files.isDirectory(path)) {
//            throw new IllegalArgumentException("Path must point to existing file");
//        }
//        var provider = (Function0<InputStream>) () -> Files.newInputStream(path, StandardOpenOption.READ);
//        var raw = path.getFileName().toString();
//        var uri = URI.create(raw);
//        return new ProvidedOpenAPIDocument(uri, name, provider);
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given {@link Path}.
//     *
//     * @param path the specified {@link Path} to document file
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(Path path) {
//        return of(path, null);
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given {@link File} and document name.
//     *
//     * @param file the specified {@link File} instance, pointing to document file
//     * @param name the specified document name, may be null
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(File file, String name) {
//        if (!file.isFile()) {
//            throw new IllegalArgumentException("File must exists");
//        }
//        var provider = (Function0<InputStream>) () -> new FileInputStream(file);
//        var raw = file.getName();
//        var uri = URI.create(raw);
//        return new ProvidedOpenAPIDocument(uri, name, provider);
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given {@link File}.
//     *
//     * @param file the specified {@link File} instance, pointing to document file
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(File file) {
//        return of(file, null);
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given path string and document name.
//     *
//     * @param path the specified path string, must be non-null
//     * @param name the specified document name, may be null
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(String path, String name) {
//        return of(Path.of(path), name);
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given path string.
//     *
//     * @param path the specified path string, must be non-null
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(String path) {
//        return of(Path.of(path), null);
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given {@link Class}, resource string and document name.
//     *
//     * @param clazz    the specified {@link Class} instance, must be non-null
//     * @param resource the specified resource string, must be non-null
//     * @param name     the specified document name, may be null
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(Class<?> clazz, String resource, String name) {
//        var found = clazz.getResource(resource);
//        if (found == null) {
//            throw new IllegalStateException("Resource not found");
//        }
//        var uri = URI.create(resource);
//        return new ProvidedOpenAPIDocument(uri, name, found::openStream);
//    }
//
//    /**
//     * Creates an {@link OpenAPIDocument} instance from given {@link Class} and resource string.
//     *
//     * @param clazz    the specified {@link Class} instance, must be non-null
//     * @param resource the specified resource string, must be non-null
//     * @return the {@link OpenAPIDocument} instance
//     */
//    public static OpenAPIDocument of(Class<?> clazz, String resource) {
//        return of(clazz, resource, null);
//    }
//}
