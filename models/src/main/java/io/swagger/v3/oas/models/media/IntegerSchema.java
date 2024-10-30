package io.swagger.v3.oas.models.media;

import java.text.NumberFormat;

/**
 * IntegerSchema
 */
public class IntegerSchema extends Schema<Number> {

    public IntegerSchema() {
        super("integer", "int32");
    }

    @Override
    public IntegerSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public IntegerSchema format(String format) {
        super.setFormat(format);
        return this;
    }

    public IntegerSchema _default(Number _default) {
        super.setDefault(_default);
        return this;
    }

    @Override
    protected Number cast(Object value) {
        if (value == null) {
            return null;
        }
        try {
            var casted = NumberFormat.getInstance().parse(value.toString());
            if (Integer.MIN_VALUE <= casted.longValue() && casted.longValue() <= Integer.MAX_VALUE) {
                return Integer.parseInt(value.toString());
            } else {
                return Long.parseLong(value.toString());
            }
        } catch (Throwable e) {
            return null;
        }
    }

    public IntegerSchema addEnumItem(Number _enumItem) {
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
        return "class IntegerSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
