module io.github.amayaframework.swagger {
    // TODO Remove soon
    requires static io.swagger.v3.oas.models;
    requires io.github.amayaframework.openui;
    requires io.github.amayaframework.swaggerui;
    requires static io.github.amayaframework.di;
    requires io.github.amayaframework.jetty;
    requires io.github.amayaframework.core;
    // Imports
    requires com.github.romanqed.jfunc;
    requires io.github.amayaframework.http;
    requires io.github.amayaframework.context;
    requires io.github.amayaframework.web;
    // Exports
    exports io.github.amayaframework.swagger;
}
