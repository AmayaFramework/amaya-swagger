package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Function0;

import java.io.InputStream;
import java.net.URI;

public interface OpenAPIDocument {

    String getTitle();

    URI getPath();

    Function0<InputStream> getProvider();
}
