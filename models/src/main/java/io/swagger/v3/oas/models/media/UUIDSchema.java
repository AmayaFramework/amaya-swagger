package io.swagger.v3.oas.models.media;

import java.util.List;
import java.util.UUID;

/**
 * UUIDSchema
 */
public class UUIDSchema extends Schema<UUID> {

    public UUIDSchema() {
        super("string", "uuid");
    }

    @Override
    public UUIDSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public UUIDSchema format(String format) {
        super.setFormat(format);
        return this;
    }

    public UUIDSchema _default(UUID _default) {
        super.setDefault(_default);
        return this;
    }

    public UUIDSchema _default(String _default) {
        if (_default != null) {
            super.setDefault(UUID.fromString(_default));
        }
        return this;
    }

    public UUIDSchema _enum(List<UUID> _enum) {
        super.setEnum(_enum);
        return this;
    }

    public UUIDSchema addEnumItem(UUID _enumItem) {
        super.addEnumItemObject(_enumItem);
        return this;
    }

    @Override
    protected UUID cast(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return UUID.fromString(value.toString());
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
        return "class UUIDSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
