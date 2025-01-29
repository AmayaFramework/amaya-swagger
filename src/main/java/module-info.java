module io.github.amayaframework.swagger {
    // TODO
    requires static io.swagger.v3.oas.models;
    requires io.github.amayaframework.openui;
    requires io.github.amayaframework.swaggerui;
    // Imports
    requires io.github.amayaframework.core;
    requires io.github.amayaframework.web;
    requires io.github.amayaframework.jetty;
    requires com.github.romanqed.jfunc;
    requires io.github.amayaframework.context;
    requires io.github.amayaframework.http;
    // Exports
    exports io.github.amayaframework.swagger;
}
