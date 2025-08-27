package io.github.amayaframework.compress;

import java.io.IOException;
import java.io.OutputStream;

public interface Encoder {

    String name();

    OutputStream encode(OutputStream stream) throws IOException;
}
