package io.swagger.v3.oas.models.media;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateSchema
 */
public class DateSchema extends Schema<Date> {

    public DateSchema() {
        super("string", "date");
    }

    @Override
    public DateSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public DateSchema format(String format) {
        super.setFormat(format);
        return this;
    }

    public DateSchema _default(Date _default) {
        super.setDefault(_default);
        return this;
    }

    @Override
    protected Date cast(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date) value;
        }
        if (value instanceof String) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd Z").parse(value + " UTC");
            } catch (Throwable e) {
                return null;
            }
        }
        return null;
    }

    public DateSchema addEnumItem(Date _enumItem) {
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
        return "class DateSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
