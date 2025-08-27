package io.github.amayaframework.openui;

import io.github.amayaframework.http.MimeType;

import java.io.IOException;
import java.io.InputStream;

public interface Part {

    String name();

    MimeType mimeType();

    String charset();

    InputStream inputStream() throws IOException;
}
