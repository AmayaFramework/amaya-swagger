package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeData;
import io.github.amayaframework.http.MimeType;

import java.util.Map;

final class MapMimeTyper implements MimeTyper {
    private static final MimeType YAML_TYPE = new MimeType("application", "x-yaml", true);
    private static final MimeData YAML_DATA = new MimeData(YAML_TYPE, "charset", "utf-8");
    private static final MimeData XML_DATA = new MimeData(MimeType.APPLICATION_XML, "charset", "utf-8");
    private static final MimeData HTML_DATA = new MimeData(MimeType.HTML, "charset", "utf-8");
    private static final MimeData CSS_DATA = new MimeData(MimeType.CSS, "charset", "utf-8");
    private static final MimeData JS_DATA = new MimeData(MimeType.JAVASCRIPT, "charset", "utf-8");
    private static final MimeData JSON_DATA = new MimeData(MimeType.JSON, "charset", "utf-8");
    private static final MimeData JPEG_DATA = new MimeData(MimeType.JPEG);
    private static final MimeData PNG_DATA = new MimeData(MimeType.PNG);
    private static final MimeData GIF_DATA = new MimeData(MimeType.GIF);
    private static final Map<String, MimeData> TYPES = Map.of(
            "yaml", YAML_DATA,
            "xml", XML_DATA,
            "html", HTML_DATA,
            "css", CSS_DATA,
            "js", JS_DATA,
            "json", JSON_DATA,
            "jpeg", JPEG_DATA,
            "jpg", JPEG_DATA,
            "png", PNG_DATA,
            "gif", GIF_DATA
    );

    @Override
    public MimeData get(String extension) {
        return TYPES.get(extension);
    }
}
