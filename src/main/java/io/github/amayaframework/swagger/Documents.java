package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Function0;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class Documents {
    private Documents() {
    }

    public static OpenAPIDocument of(URL url, String name) {
        try {
            return new RemoteOpenAPIDocument(url.toURI(), name);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static OpenAPIDocument of(URL url) {
        return of(url, null);
    }

    public static OpenAPIDocument of(Path path, String name) {
        if (Files.notExists(path) || Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must point to existing file");
        }
        var provider = (Function0<InputStream>) () -> Files.newInputStream(path, StandardOpenOption.READ);
        var raw = path.getFileName().toString();
        var uri = URI.create(raw);
        return new ProvidedOpenAPIDocument(uri, name, provider);
    }

    public static OpenAPIDocument of(Path path) {
        return of(path, null);
    }

    public static OpenAPIDocument of(File file, String name) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("File must exists");
        }
        var provider = (Function0<InputStream>) () -> new FileInputStream(file);
        var raw = file.getName();
        var uri = URI.create(raw);
        return new ProvidedOpenAPIDocument(uri, name, provider);
    }

    public static OpenAPIDocument of(File file) {
        return of(file, null);
    }

    public static OpenAPIDocument of(String path, String name) {
        return of(Path.of(path), name);
    }

    public static OpenAPIDocument of(String path) {
        return of(Path.of(path), null);
    }

    public static OpenAPIDocument of(Class<?> clazz, String resource, String name) {
        var found = clazz.getResource(resource);
        if (found == null) {
            throw new IllegalStateException("Resource not found");
        }
        var uri = URI.create(resource);
        return new ProvidedOpenAPIDocument(uri, name, found::openStream);
    }

    public static OpenAPIDocument of(Class<?> clazz, String resource) {
        return of(clazz, resource, null);
    }
}
