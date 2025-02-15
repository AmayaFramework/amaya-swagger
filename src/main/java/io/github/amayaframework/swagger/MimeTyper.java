package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeData;

public interface MimeTyper {

    MimeData get(String extension);
}
