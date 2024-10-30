package io.swagger.v3.oas.models.media;

import java.util.List;
import java.util.Objects;

/**
 * StringSchema
 */
public class StringSchema extends Schema<String> {

    public StringSchema() {
        super("string", null);
    }

    @Override
    public StringSchema type(String type) {
        super.setType(type);
        return this;
    }

    public StringSchema _default(String _default) {
        super.setDefault(_default);
        return this;
    }

    public StringSchema _enum(List<String> _enum) {
        super.setEnum(_enum);
        return this;
    }

    public StringSchema addEnumItem(String _enumItem) {
        super.addEnumItemObject(_enumItem);
        return this;
    }

    @Override
    protected String cast(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return value.toString();
        } catch (Throwable e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "class StringSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
