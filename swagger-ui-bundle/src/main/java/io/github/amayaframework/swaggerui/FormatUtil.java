package io.github.amayaframework.swaggerui;

import io.github.amayaframework.openui.ApiEntry;

final class FormatUtil {
    static final String REPLACE_TEMPLATE = "%urls";

    private FormatUtil() {
    }

    static String getUrlEntry(String url) {
        return "url:\"" + url + '"';
    }

    static String getUrlEntry(String url, String name) {
        return "{url:\"" + url + "\",name:\"" + name + "\"}";
    }

    static String setUrl(String index, String url) {
        var value = getUrlEntry(url);
        return index.replace(REPLACE_TEMPLATE, value);
    }

    static String setUrlEntry(String index, ApiEntry entry) {
        var value = getUrlEntry(entry.getURL().toString(), entry.getName());
        return index.replace(REPLACE_TEMPLATE, "urls:[" + value + "]");
    }

    static String setUrls(String index, ApiEntry... entries) {
        var builder = new StringBuilder("urls:[");
        for (var entry : entries) {
            var item = getUrlEntry(entry.getURL().toString(), entry.getName());
            builder.append(item);
            builder.append(',');
        }
        builder.append(']');
        return index.replace(REPLACE_TEMPLATE, builder.toString());
    }

    static String setUrls(String index, Iterable<ApiEntry> entries) {
        var builder = new StringBuilder("urls:[");
        for (var entry : entries) {
            var item = getUrlEntry(entry.getURL().toString(), entry.getName());
            builder.append(item);
            builder.append(',');
        }
        builder.append(']');
        return index.replace(REPLACE_TEMPLATE, builder.toString());
    }
}
