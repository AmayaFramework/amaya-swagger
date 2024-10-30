package io.swagger.v3.oas.models.media;

/**
 * ArraySchema
 */
public class ArraySchema extends Schema<Object> {

    public ArraySchema() {
        super("array", null);
    }

    @Override
    public ArraySchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ArraySchema items(Schema items) {
        super.setItems(items);
        return this;
    }

    @Override
    public String toString() {
        return "class ArraySchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
