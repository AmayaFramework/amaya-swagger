package io.github.amayaframework.openui;

import java.net.URI;

public interface OpenUI {

    URI root();

    Part index();

    Part part(String name);

    Iterable<Part> parts();
}
