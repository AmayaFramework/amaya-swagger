package io.swagger.v3.oas.models.callbacks;

import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.annotations.OpenAPI31;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Callback
 *
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#callbackObject"
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.1.0/versions/3.1.0.md#callbackObject"
 */
public class Callback extends LinkedHashMap<String, PathItem> {
    private Map<String, Object> extensions = null;
    private String $ref = null;

    public Callback() {
    }

    /**
     * @since 2.0.3
     */
    public String get$ref() {
        return $ref;
    }

    /**
     * @since 2.0.3
     */
    public void set$ref(String $ref) {
        if ($ref != null && ($ref.indexOf('.') == -1 && $ref.indexOf('/') == -1)) {
            $ref = "#/components/callbacks/" + $ref;
        }
        this.$ref = $ref;
    }

    /**
     * @since 2.0.3
     */
    public Callback $ref(String $ref) {
        set$ref($ref);
        return this;
    }

    public Callback addPathItem(String name, PathItem item) {
        this.put(name, item);
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
        var callback = (Callback) o;
        if (!Objects.equals($ref, callback.$ref)) {
            return false;
        }
        return Objects.equals(this.extensions, callback.extensions) &&
                super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extensions, $ref, super.hashCode());
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
            this.extensions = new LinkedHashMap<>();
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

    public Callback extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    @Override
    public String toString() {
        return "class Callback {\n" +
                "    $ref: " + toIndentedString($ref) + "\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
