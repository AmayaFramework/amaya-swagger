package io.github.amayaframework.openui;

/**
 * TODO
 */
public interface OpenUi {

    /**
     * TODO
     * @return
     */
    Part index();

    /**
     * TODO
     * @param name
     * @return
     */
    Part part(String name);

    /**
     * TODO
     * @return
     */
    Iterable<Part> parts();
}
