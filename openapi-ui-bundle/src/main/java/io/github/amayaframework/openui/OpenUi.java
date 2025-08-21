package io.github.amayaframework.openui;

public interface OpenUi {

    Part index();

    Part part(String name);

    Iterable<Part> parts();
}
