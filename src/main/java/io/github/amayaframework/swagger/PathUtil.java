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
        return normalize(uri.normalize().getRawPath());
    }

    static String normalizeTail(String path) {
        var last = path.length() - 1;
        if (path.charAt(last) == '/') {
            return path.substring(0, last);
        }
        return path;
    }
}
