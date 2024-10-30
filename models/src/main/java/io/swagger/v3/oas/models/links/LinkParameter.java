package io.swagger.v3.oas.models.links;

import io.swagger.v3.oas.models.annotations.OpenAPI31;

import java.util.Map;
import java.util.Objects;

/**
 * LinkParameter
 */
public class LinkParameter {
    private String value;
    private Map<String, Object> extensions = null;

    public LinkParameter() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LinkParameter value(String value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var linkParameters = (LinkParameter) o;
        return Objects.equals(this.value, linkParameters.value) &&
                Objects.equals(this.extensions, linkParameters.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, extensions);
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new java.util.LinkedHashMap<>();
        }
        this.extensions.put(name, value);
    }

    @OpenAPI31
    public void addExtension31(String name, Object value) {
        if (name != null && (name.startsWith("x-oas-") || name.startsWith("x-oai-"))) {
            return;
        }
        addExtension(name, value);
    }

    public LinkParameter extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    @Override
    public String toString() {
        return "class LinkParameter {\n" +
                "}";
    }
}