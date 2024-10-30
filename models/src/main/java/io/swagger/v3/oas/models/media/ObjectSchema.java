package io.swagger.v3.oas.models.media;

/**
 * ObjectSchema
 */
public class ObjectSchema extends Schema<Object> {

    public ObjectSchema() {
        super("object", null);
    }

    @Override
    public ObjectSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public ObjectSchema example(Object example) {
        if (example == null) {
            super.setExample(null);
        } else {
            super.setExample(example.toString());
        }
        return this;
    }

    @Override
    protected Object cast(Object value) {
        return value;
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
        return "class ObjectSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
