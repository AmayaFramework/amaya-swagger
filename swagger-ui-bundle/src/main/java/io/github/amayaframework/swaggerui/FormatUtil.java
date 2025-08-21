//package io.github.amayaframework.swaggerui;
//
//import io.github.amayaframework.openui.ApiEntry;
//
//import java.net.URI;
//
//final class FormatUtil {
//    static final char SLASH = '/';
//    static final String URLS_TEMPLATE = "%urls";
//    static final String ROOT_TEMPLATE = "%root";
//
//    private FormatUtil() {
//    }
//
//    static String normalizeUri(String uri) {
//        if (uri.isEmpty()) {
//            return ".";
//        }
//        var last = uri.length() - 1;
//        if (uri.charAt(last) == SLASH) {
//            return uri.substring(0, last);
//        }
//        return uri;
//    }
//
//    static String setRoot(String index, String root) {
//        root = normalizeUri(root);
//        return index.replace(ROOT_TEMPLATE, root);
//    }
//
//    static String setRoot(String index, URI root) {
//        return setRoot(index, root.normalize().toString());
//    }
//
//    static String getUrlEntry(String url) {
//        return "url:\"" + url + '"';
//    }
//
//    static String getUrlEntry(String url, String name) {
//        return "{url:\"" + url + "\",name:\"" + name + "\"}";
//    }
//
//    static String setUrl(String index, String url) {
//        var value = getUrlEntry(url);
//        return index.replace(URLS_TEMPLATE, value);
//    }
//
//    static String setUrlEntry(String index, ApiEntry entry) {
//        var value = getUrlEntry(entry.getURI().toString(), entry.getName());
//        return index.replace(URLS_TEMPLATE, "urls:[" + value + "]");
//    }
//
//    static String setUrls(String index, ApiEntry... entries) {
//        var builder = new StringBuilder("urls:[");
//        for (var entry : entries) {
//            var item = getUrlEntry(entry.getURI().toString(), entry.getName());
//            builder.append(item);
//            builder.append(',');
//        }
//        builder.append(']');
//        return index.replace(URLS_TEMPLATE, builder.toString());
//    }
//
//    static String setUrls(String index, Iterable<ApiEntry> entries) {
//        var builder = new StringBuilder("urls:[");
//        for (var entry : entries) {
//            var item = getUrlEntry(entry.getURI().toString(), entry.getName());
//            builder.append(item);
//            builder.append(',');
//        }
//        builder.append(']');
//        return index.replace(URLS_TEMPLATE, builder.toString());
//    }
//}
