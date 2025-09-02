package io.github.amayaframework.swaggerui;

import io.github.amayaframework.openui.OpenApiEntry;

import java.net.URI;

final class StringUtil {
    static final String EMPTY_URLS = "urls:[]";
    static final String URLS_PREFIX = "urls:[";
    static final String MARKER = "//%";
    static final String MARKER_COMMA = MARKER + ',';
    static final String INIT_JS = "window.onload=function(){window.ui=SwaggerUIBundle({//%,dom_id:\"#swagger-ui\",deepLinking:!0,presets:[SwaggerUIBundle.presets.apis,SwaggerUIStandalonePreset],plugins:[SwaggerUIBundle.plugins.DownloadUrl],layout:\"StandaloneLayout\"})};";
    private StringUtil() {
    }

    static String singleUrl(String url) {
        return "url:\"" + url + "\"";
    }

    static String urlEntry(String url, String name) {
        return "{\"url\":\"" + url + "\",\"name\":\"" + name + "\"}";
    }

    static String generateInit() {
        return INIT_JS.replace(MARKER_COMMA, "");
    }

    static String generateInit(URI uri) {
        return INIT_JS.replace(MARKER, singleUrl(uri.toString()));
    }

    static String generateInit(OpenApiEntry entry) {
        var array = URLS_PREFIX + urlEntry(entry.getURI().toString(), entry.getName()) + "]";
        return INIT_JS.replace(MARKER, array);
    }

    static String generateInit(OpenApiEntry[] entries) {
        if (entries.length == 0) {
            return EMPTY_URLS;
        }
        var array = new StringBuilder(URLS_PREFIX);
        var first = entries[0];
        array.append(urlEntry(first.getURI().toString(), first.getName()));
        for (var i = 1; i < entries.length; ++i) {
            var entry = entries[i];
            array.append(',').append(urlEntry(entry.getURI().toString(), entry.getName()));
        }
        array.append(']');
        return INIT_JS.replace(MARKER, array.toString());
    }

    static String generateInit(Iterable<OpenApiEntry> entries) {
        var iterator = entries.iterator();
        if (!iterator.hasNext()) {
            return EMPTY_URLS;
        }
        var array = new StringBuilder(URLS_PREFIX);
        var first = iterator.next();
        array.append(urlEntry(first.getURI().toString(), first.getName()));
        while (iterator.hasNext()) {
            var entry = iterator.next();
            array.append(',').append(urlEntry(entry.getURI().toString(), entry.getName()));
        }
        array.append(']');
        return INIT_JS.replace(MARKER, array.toString());
    }
}
