module io.github.amayaframework.swagger {
    // TODO
    requires static io.swagger.v3.oas.models;
    // Imports
    requires io.github.amayaframework.core;
    requires io.github.amayaframework.web;
    requires io.github.amayaframework.jetty;
    requires com.github.romanqed.jfunc;
    requires io.github.amayaframework.context;
    // Exports
    exports io.github.amayaframework.swagger;
}
