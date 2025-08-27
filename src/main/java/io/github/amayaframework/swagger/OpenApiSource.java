package io.github.amayaframework.swagger;

import java.net.URI;

public interface OpenApiSource {

    URI uri();

    String name();

    OpenApiFormat format();

    InputStreamProvider provider();
}
