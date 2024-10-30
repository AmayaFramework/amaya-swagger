package io.swagger.v3.oas.models.parameters;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.media.Content;

import java.util.Map;
import java.util.Objects;

/**
 * RequestBody
 *
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#requestBodyObject"
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.1.0/versions/3.1.0.md#requestBodyObject"
 */
public class RequestBody {
    private String description = null;
    private Content content = null;
    private Boolean required = null;
    private Map<String, Object> extensions = null;
    private String $ref = null;

    /**
     * returns the description property from a RequestBody instance.
     *
     * @return String description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestBody description(String description) {
        this.description = description;
        return this;
    }

    /**
     * returns the content property from a RequestBody instance.
     *
     * @return Content content
     **/
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public RequestBody content(Content content) {
        this.content = content;
        return this;
    }

    /**
     * returns the required property from a RequestBody instance.
     *
     * @return Boolean required
     **/
    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public RequestBody required(Boolean required) {
        this.required = required;
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

    public RequestBody extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        if ($ref != null && ($ref.indexOf('.') == -1 && $ref.indexOf('/') == -1)) {
            $ref = "#/components/requestBodies/" + $ref;
        }
        this.$ref = $ref;
    }

    public RequestBody $ref(String $ref) {
        set$ref($ref);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestBody)) {
            return false;
        }

        var that = (RequestBody) o;

        if (!Objects.equals(description, that.description)) {
            return false;
        }
        if (!Objects.equals(content, that.content)) {
            return false;
        }
        if (!Objects.equals(required, that.required)) {
            return false;
        }
        if (!Objects.equals(extensions, that.extensions)) {
            return false;
        }
        return Objects.equals($ref, that.$ref);
    }

    @Override
    public int hashCode() {
        var result = description != null ? description.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (required != null ? required.hashCode() : 0);
        result = 31 * result + (extensions != null ? extensions.hashCode() : 0);
        result = 31 * result + ($ref != null ? $ref.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "class RequestBody {\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    content: " + toIndentedString(content) + "\n" +
                "    required: " + toIndentedString(required) + "\n" +
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