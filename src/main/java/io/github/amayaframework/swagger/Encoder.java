package io.github.amayaframework.swagger;

import java.io.OutputStream;

public interface Encoder {
    String name();

    OutputStream encode(OutputStream stream);
}
