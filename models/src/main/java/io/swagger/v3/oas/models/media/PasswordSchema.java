package io.swagger.v3.oas.models.media;

import java.util.Objects;

/**
 * PasswordSchema
 */
public class PasswordSchema extends Schema<String> {

    public PasswordSchema() {
        super("string", "password");
    }

    @Override
    public PasswordSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public PasswordSchema format(String format) {
        super.setFormat(format);
        return this;
    }

    public PasswordSchema _default(String _default) {
        super.setDefault(_default);
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

    public PasswordSchema addEnumItem(String _enumItem) {
        super.addEnumItemObject(_enumItem);
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
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "class PasswordSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
