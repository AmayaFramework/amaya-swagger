package io.github.amayaframework.openui;

/**
 * Provides a server-side representation of Swagger UI.
 * An {@link OpenUi} instance serves the index page and related static assets
 * (HTML, CSS, JavaScript, etc.) required to render the UI in the browser.
 */
public interface OpenUi {

    /**
     * Returns the main entry point of the UI (usually {@code "index.html"}).
     *
     * @return the index part
     */
    Part index();

    /**
     * Returns a part by its name, such as a JavaScript or CSS resource.
     *
     * @param name the resource name
     * @return the part, or {@code null} if not found
     */
    Part part(String name);

    /**
     * Returns all parts that make up this UI instance, including the index
     * and all supporting assets.
     *
     * @return iterable over parts
     */
    Iterable<Part> parts();
}
