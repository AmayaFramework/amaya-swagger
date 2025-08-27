package io.github.amayaframework.swagger;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface InputStreamProvider {

    InputStream get() throws IOException;
}
