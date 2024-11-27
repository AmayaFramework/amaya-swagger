package io.github.amayaframework.swaggerui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

final class Util {
    static final String PACKAGE = "io/github/amayaframework/swaggerui/";
    static final ClassLoader LOADER = Util.class.getClassLoader();
    static final String INDEX = "index.html";

    private Util() {
    }

    static InputStream getInputStream(String resource) {
        return LOADER.getResourceAsStream(PACKAGE + resource);
    }

    static String getIndex() throws IOException {
        var stream = getInputStream(Util.INDEX);
        try (stream; var reader = new BufferedReader(new InputStreamReader(stream))) {
            return reader.lines().reduce("", (l, r) -> l + "\n" + r);
        }
    }
}
