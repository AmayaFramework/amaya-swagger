package io.swagger.v3.oas.models.info;

import io.swagger.v3.oas.models.annotations.OpenAPI31;

import java.util.Map;
import java.util.Objects;

/**
 * Contact
 *
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#contactObject"
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.1.0/versions/3.1.0.md#contactObject"
 */
public class Contact {
    private String name = null;
    private String url = null;
    private String email = null;
    private Map<String, Object> extensions = null;

    /**
     * returns the name property from a Contact instance.
     *
     * @return String name
     **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact name(String name) {
        this.name = name;
        return this;
    }

    /**
     * returns the url property from a Contact instance.
     *
     * @return String url
     **/
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Contact url(String url) {
        this.url = url;
        return this;
    }

    /**
     * returns the email property from a Contact instance.
     *
     * @return String email
     **/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact email(String email) {
        this.email = email;
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
        var contact = (Contact) o;
        return Objects.equals(this.name, contact.name) &&
                Objects.equals(this.url, contact.url) &&
                Objects.equals(this.email, contact.email) &&
                Objects.equals(this.extensions, contact.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, email, extensions);
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

    public Contact extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    @Override
    public String toString() {
        return "class Contact {\n" +
                "    name: " + toIndentedString(name) + "\n" +
                "    url: " + toIndentedString(url) + "\n" +
                "    email: " + toIndentedString(email) + "\n" +
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
