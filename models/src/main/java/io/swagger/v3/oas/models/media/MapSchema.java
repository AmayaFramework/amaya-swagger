package io.swagger.v3.oas.models.media;

import java.util.Objects;

/**
 * MapSchema
 */
public class MapSchema extends Schema<Object> {

    public MapSchema() {
        super("object", null);
    }

    @Override
    public MapSchema type(String type) {
        super.setType(type);
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
        return "class MapSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
