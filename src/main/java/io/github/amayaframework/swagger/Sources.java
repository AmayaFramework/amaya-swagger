package io.github.amayaframework.swagger;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * Factory methods for creating {@link OpenApiSource} instances.
 * <p>
 * Supports:
 * <ul>
 *   <li>Remote sources backed by {@link RemoteApiSource} (created from absolute URIs).</li>
 *   <li>Local sources backed by {@link StreamApiSource} (created from {@link Path} or string paths).</li>
 * </ul>
 * Local sources may either explicitly specify an {@link OpenApiFormat} or
 * rely on automatic detection from the file extension:
 * <ul>
 *   <li>{@code .json} → {@link OpenApiFormat#JSON}</li>
 *   <li>{@code .yml} or {@code .yaml} → {@link OpenApiFormat#YAML}</li>
 * </ul>
 */
public final class Sources {
    private Sources() {
    }

    /**
     * Creates a remote API source pointing to the given URI.
     *
     * @param uri  the absolute URI of the remote OpenAPI document
     * @param name a human-readable name, may be {@code null}
     * @return a new {@link RemoteApiSource}
     */
    public static OpenApiSource of(URI uri, String name) {
        return new RemoteApiSource(uri, name);
    }

    /**
     * Creates a remote API source pointing to the given URI string.
     *
     * @param uri  the absolute URI of the remote OpenAPI document as a string
     * @param name a human-readable name, may be {@code null}
     * @return a new {@link RemoteApiSource}
     */
    public static OpenApiSource of(String uri, String name) {
        return of(URI.create(uri), name);
    }

    /**
     * Creates a remote API source without an explicit name.
     *
     * @param uri the absolute URI of the remote OpenAPI document
     * @return a new {@link RemoteApiSource}
     */
    public static OpenApiSource of(URI uri) {
        return of(uri, (String) null);
    }

    /**
     * Creates a remote API source without an explicit name.
     *
     * @param uri the absolute URI of the remote OpenAPI document as a string
     * @return a new {@link RemoteApiSource}
     */
    public static OpenApiSource of(String uri) {
        return of(uri, (String) null);
    }

    /**
     * Creates a local API source backed by a file, with explicitly provided format.
     *
     * @param uri    a relative identifier URI for this source (must not be absolute)
     * @param name   a human-readable name, may be {@code null}
     * @param path   the file path to read from
     * @param format the OpenAPI format
     * @return a new {@link StreamApiSource}
     * @throws IllegalArgumentException if {@code uri} is absolute
     */
    public static OpenApiSource of(URI uri, String name, Path path, OpenApiFormat format) {
        if (uri.isAbsolute()) {
            throw new IllegalArgumentException("Absolute URIs are not supported for local sources: " + uri);
        }
        Objects.requireNonNull(path, "Path must not be null");
        Objects.requireNonNull(format, "Format must not be null");
        return new StreamApiSource(uri, name, format, () -> Files.newInputStream(path, StandardOpenOption.READ));
    }

    /**
     * Creates a local API source backed by a file, with explicitly provided format.
     *
     * @param uri    a relative identifier URI string for this source (must not be absolute)
     * @param name   a human-readable name, may be {@code null}
     * @param path   the file path to read from
     * @param format the OpenAPI format
     * @return a new {@link StreamApiSource}
     * @throws IllegalArgumentException if {@code uri} is absolute
     */
    public static OpenApiSource of(String uri, String name, Path path, OpenApiFormat format) {
        return of(URI.create(uri), name, path, format);
    }

    /**
     * Creates a local API source backed by a file, with explicitly provided format, without an explicit name.
     *
     * @param uri    a relative identifier URI for this source (must not be absolute)
     * @param path   the file path to read from
     * @param format the OpenAPI format
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(URI uri, Path path, OpenApiFormat format) {
        return of(uri, null, path, format);
    }

    /**
     * Creates a local API source backed by a file, with explicitly provided format, without an explicit name.
     *
     * @param uri    a relative identifier URI string for this source (must not be absolute)
     * @param path   the file path to read from
     * @param format the OpenAPI format
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(String uri, Path path, OpenApiFormat format) {
        return of(URI.create(uri), path, format);
    }

    /**
     * Creates a local API source backed by a file path string, with explicitly provided format.
     *
     * @param uri    a relative identifier URI for this source (must not be absolute)
     * @param name   a human-readable name, may be {@code null}
     * @param path   the file path as a string
     * @param format the OpenAPI format
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(URI uri, String name, String path, OpenApiFormat format) {
        return of(uri, name, Path.of(path), format);
    }

    /**
     * Creates a local API source backed by a file path string, with explicitly provided format.
     *
     * @param uri    a relative identifier URI string for this source (must not be absolute)
     * @param name   a human-readable name, may be {@code null}
     * @param path   the file path as a string
     * @param format the OpenAPI format
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(String uri, String name, String path, OpenApiFormat format) {
        return of(uri, name, Path.of(path), format);
    }

    /**
     * Creates a local API source backed by a file path string, with explicitly provided format, without an explicit name.
     *
     * @param uri    a relative identifier URI for this source (must not be absolute)
     * @param path   the file path as a string
     * @param format the OpenAPI format
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(URI uri, String path, OpenApiFormat format) {
        return of(uri, null, Path.of(path), format);
    }

    /**
     * Creates a local API source backed by a file path string, with explicitly provided format, without an explicit name.
     *
     * @param uri    a relative identifier URI string for this source (must not be absolute)
     * @param path   the file path as a string
     * @param format the OpenAPI format
     * @return a new {@link StreamApiSource}
     */
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
        throw new IllegalStateException("Unsupported file extension for OpenAPI source: " + ext);
    }

    /**
     * Creates a local API source backed by a file, automatically detecting format from its extension.
     *
     * @param uri  a relative identifier URI for this source (must not be absolute)
     * @param name a human-readable name, may be {@code null}
     * @param path the file path to read from
     * @return a new {@link StreamApiSource}
     * @throws IllegalStateException if the extension is missing or unsupported
     */
    public static OpenApiSource of(URI uri, String name, Path path) {
        var ext = extractExt(path);
        if (ext == null) {
            throw new IllegalStateException("Missing file extension for OpenAPI source: " + path);
        }
        return of(uri, name, path, decideFormat(ext));
    }

    /**
     * Creates a local API source backed by a file, automatically detecting format from its extension.
     *
     * @param uri  a relative identifier URI string for this source (must not be absolute)
     * @param name a human-readable name, may be {@code null}
     * @param path the file path to read from
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(String uri, String name, Path path) {
        return of(URI.create(uri), name, path);
    }

    /**
     * Creates a local API source backed by a file, automatically detecting format from its extension,
     * without an explicit name.
     *
     * @param uri  a relative identifier URI for this source (must not be absolute)
     * @param path the file path to read from
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(URI uri, Path path) {
        return of(uri, null, path);
    }

    /**
     * Creates a local API source backed by a file, automatically detecting format from its extension,
     * without an explicit name.
     *
     * @param uri  a relative identifier URI string for this source (must not be absolute)
     * @param path the file path to read from
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(String uri, Path path) {
        return of(uri, null, path);
    }

    /**
     * Creates a local API source backed by a file path string, automatically detecting format from its extension.
     *
     * @param uri  a relative identifier URI for this source (must not be absolute)
     * @param name a human-readable name, may be {@code null}
     * @param path the file path as a string
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(URI uri, String name, String path) {
        return of(uri, name, Path.of(path));
    }

    /**
     * Creates a local API source backed by a file path string, automatically detecting format from its extension.
     *
     * @param uri  a relative identifier URI string for this source (must not be absolute)
     * @param name a human-readable name, may be {@code null}
     * @param path the file path as a string
     * @return a new {@link StreamApiSource}
     */
    public static OpenApiSource of(String uri, String name, String path) {
        return of(uri, name, Path.of(path));
    }
}
