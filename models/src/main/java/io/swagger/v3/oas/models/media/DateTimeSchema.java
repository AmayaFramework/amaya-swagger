package io.swagger.v3.oas.models.media;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * DateTimeSchema
 */
public class DateTimeSchema extends Schema<OffsetDateTime> {

    public DateTimeSchema() {
        super("string", "date-time");
    }

    @Override
    public DateTimeSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public DateTimeSchema format(String format) {
        super.setFormat(format);
        return this;
    }

    public DateTimeSchema _default(Date _default) {
        super.setDefault(_default);
        return this;
    }

    @Override
    protected OffsetDateTime cast(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return ((Date) value).toInstant().atOffset(ZoneOffset.UTC);
        }
        if (value instanceof OffsetDateTime) {
            return (OffsetDateTime) value;
        }
        if (value instanceof String) {
            try {
                return OffsetDateTime.parse((String) value);
            } catch (Throwable e) {
                return null;
            }
        }
        return null;
    }

    public DateTimeSchema _enum(List<OffsetDateTime> _enum) {
        super.setEnum(_enum);
        return this;
    }

    public DateTimeSchema addEnumItem(OffsetDateTime _enumItem) {
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
        return "class DateTimeSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
