package io.swagger.v3.oas.models.media;

/**
 * FileSchema
 */
public class FileSchema extends Schema<String> {

    public FileSchema() {
        super("string", "binary");
    }

    @Override
    public FileSchema type(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public FileSchema format(String format) {
        super.setFormat(format);
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
        return "class FileSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
