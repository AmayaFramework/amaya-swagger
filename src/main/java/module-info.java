/**
 * TODO
 */
module amayaframework.swagger {
    // TODO REMOVE
    requires amayaframework.core;
    requires amayaframework.tomcat;
    requires amayaframework.swaggerui;
    // Imports
    // Basic dependencies
    requires com.github.romanqed.jtype;
    requires com.github.romanqed.jsync;
    // Amaya modules
    requires amayaframework.options;
    requires amayaframework.web;
    requires amayaframework.openui;
    requires amayaframework.compress;
    requires amayaframework.environment;
    // Exports
    exports io.github.amayaframework.swagger;
}
