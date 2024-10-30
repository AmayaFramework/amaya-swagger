package io.swagger.v3.oas.models.media;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * BooleanSchema
 */
public class BooleanSchema extends Schema<Boolean> {

    public BooleanSchema() {
        super("boolean", null);
    }

    @Override
    public BooleanSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public BooleanSchema types(Set<String> types) {
        super.setTypes(types);
        return this;
    }

    public BooleanSchema _default(Boolean _default) {
        super.setDefault(_default);
        return this;
    }

    @Override
    protected Boolean cast(Object value) {
        if (value == null) {
            return null;
        }
        return Boolean.parseBoolean(value.toString());
    }

    public BooleanSchema _enum(List<Boolean> _enum) {
        this._enum = _enum;
        return this;
    }

    public BooleanSchema addEnumItem(Boolean _enumItem) {
        if (this._enum == null) {
            this._enum = new ArrayList<>();
        }
        this._enum.add(_enumItem);
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
        return "class BooleanSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
