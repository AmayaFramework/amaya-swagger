package io.swagger.v3.oas.models.examples;

import io.swagger.v3.oas.models.annotations.OpenAPI31;

import java.util.Map;
import java.util.Objects;

/**
 * Example
 *
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#exampleObject"
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.1.0/versions/3.1.0.md#exampleObject"
 */
public class Example {
    private String summary = null;
    private String description = null;
    private Object value = null;
    private String externalValue = null;
    private String $ref = null;
    private Map<String, Object> extensions = null;

    private boolean valueSetFlag;

    /**
     * returns the summary property from a Example instance.
     *
     * @return String summary
     **/
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Example summary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * returns the description property from a Example instance.
     *
     * @return String description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Example description(String description) {
        this.description = description;
        return this;
    }

    /**
     * returns the value property from a Example instance.
     *
     * @return Object value
     **/
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        valueSetFlag = true;
    }

    public Example value(Object value) {
        setValue(value);
        return this;
    }

    /**
     * returns the externalValue property from a Example instance.
     *
     * @return String externalValue
     **/
    public String getExternalValue() {
        return externalValue;
    }

    public void setExternalValue(String externalValue) {
        this.externalValue = externalValue;
    }

    public Example externalValue(String externalValue) {
        this.externalValue = externalValue;
        return this;
    }

    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && ($ref.indexOf('.') == -1 && $ref.indexOf('/') == -1)) {
            $ref = "#/components/examples/" + $ref;
        }
        this.$ref = $ref;
    }

    public Example $ref(String $ref) {
        set$ref($ref);
        return this;
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

    public Example extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public boolean getValueSetFlag() {
        return valueSetFlag;
    }

    public void setValueSetFlag(boolean valueSetFlag) {
        this.valueSetFlag = valueSetFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Example)) {
            return false;
        }

        var example = (Example) o;

        if (!Objects.equals(summary, example.summary)) {
            return false;
        }
        if (!Objects.equals(description, example.description)) {
            return false;
        }
        if (!Objects.equals(value, example.value)) {
            return false;
        }
        if (!Objects.equals(externalValue, example.externalValue)) {
            return false;
        }
        if (!Objects.equals($ref, example.$ref)) {
            return false;
        }
        return Objects.equals(extensions, example.extensions);
    }

    @Override
    public int hashCode() {
        int result = summary != null ? summary.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (externalValue != null ? externalValue.hashCode() : 0);
        result = 31 * result + ($ref != null ? $ref.hashCode() : 0);
        result = 31 * result + (extensions != null ? extensions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "class Example {\n" +
                "    summary: " + toIndentedString(summary) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    value: " + toIndentedString(value) + "\n" +
                "    externalValue: " + toIndentedString(externalValue) + "\n" +
                "    $ref: " + toIndentedString($ref) + "\n" +
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
