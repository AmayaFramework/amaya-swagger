package io.github.amayaframework.swagger;

import java.net.URI;

final class PathUtil {
    private PathUtil() {
    }

    static String normalize(String path) {
        if (path.isEmpty() || path.equals("/")) {
            return "/";
        }
        var last = path.length() - 1;
        if (path.charAt(last) == '/') {
            path = path.substring(0, last);
        }
        if (path.charAt(0) == '/') {
            return path;
        }
        return "/" + path;
    }

    static String normalize(URI uri) {
        return normalize(uri.getRawPath());
    }

    static String normalizeTail(String path) {
        if (path.isEmpty() || path.equals("/")) {
            return "";
        }
        var last = path.length() - 1;
        if (path.charAt(last) == '/') {
            path = path.substring(0, last);
        }
        if (path.charAt(0) == '/') {
            return path.substring(1);
        }
        return path;
    }

    static String normalizeTail(URI uri) {
        return normalizeTail(uri.normalize().getRawPath());
    }

    static URI getURI(URI root, String part) {
        var uri = URI.create(part).normalize();
        return URI.create(root.getPath() + "/" + uri.getPath()).normalize();
    }

    static URI getURI(URI root, URI part) {
        return getURI(root, part.getPath());
    }
}
