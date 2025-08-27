package io.github.amayaframework.swagger;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public final class Sources {
    public static OpenApiSource of(URI uri, String name) {
        return new RemoteApiSource(uri, name);
    }

    public static OpenApiSource of(String uri, String name) {
        return of(URI.create(uri), name);
    }

    public static OpenApiSource of(URI uri) {
        return of(uri, (String) null);
    }

    public static OpenApiSource of(String uri) {
        return of(uri, (String) null);
    }

    public static OpenApiSource of(URI uri, String name, Path path, OpenApiFormat format) {
        if (uri.isAbsolute()) {
            throw new IllegalArgumentException("TODO MSG");
        }
        Objects.requireNonNull(path);
        Objects.requireNonNull(format);
        return new StreamApiSource(uri, name, format, () -> Files.newInputStream(path, StandardOpenOption.READ));
    }

    public static OpenApiSource of(String uri, String name, Path path, OpenApiFormat format) {
        return of(URI.create(uri), name, path, format);
    }

    public static OpenApiSource of(URI uri, Path path, OpenApiFormat format) {
        return of(uri, null, path, format);
    }

    public static OpenApiSource of(String uri, Path path, OpenApiFormat format) {
        return of(URI.create(uri), path, format);
    }

    public static OpenApiSource of(URI uri, String name, String path, OpenApiFormat format) {
        return of(uri, name, Path.of(path), format);
    }

    public static OpenApiSource of(String uri, String name, String path, OpenApiFormat format) {
        return of(uri, name, Path.of(path), format);
    }

    public static OpenApiSource of(URI uri, String path, OpenApiFormat format) {
        return of(uri, null, Path.of(path), format);
    }

    public static OpenApiSource of(String uri, String path, OpenApiFormat format) {
        return of(uri, null, Path.of(path), format);
    }

    private static String extractExt(Path path) {
        var filename = path.getFileName().toString();
        var index = filename.indexOf('.');
        if (index < 0) {
            return null;
        }
        return filename.substring(index + 1);
    }

    private static OpenApiFormat decideFormat(String ext) {
        if (ext.equals("json")) {
            return OpenApiFormat.JSON;
        }
        if (ext.equals("yml") || ext.equals("yaml")) {
            return OpenApiFormat.YAML;
        }
        return null;
    }

    public static OpenApiSource of(URI uri, String name, Path path) {
        var ext = extractExt(path);
        if (ext == null) {
            throw new IllegalStateException("TODO MSG");
        }
        var format = decideFormat(ext);
        if (format == null) {
            throw new IllegalStateException("TODO MSG");
        }
        return of(uri, name, path, format);
    }

    public static OpenApiSource of(String uri, String name, Path path) {
        return of(URI.create(uri), name, path);
    }

    public static OpenApiSource of(URI uri, Path path) {
        return of(uri, null, path);
    }

    public static OpenApiSource of(String uri, Path path) {
        return of(uri, null, path);
    }

    public static OpenApiSource of(URI uri, String name, String path) {
        return of(uri, name, Path.of(path));
    }

    public static OpenApiSource of(String uri, String name, String path) {
        return of(uri, name, Path.of(path));
    }
}
