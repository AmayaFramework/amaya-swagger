package io.swagger.v3.oas.models.media;

import io.swagger.v3.oas.models.SpecVersion;

/**
 * JsonSchema
 */
public class JsonSchema extends Schema<Object> {

    public JsonSchema() {
        specVersion(SpecVersion.V31);
    }

    @Override
    public String toString() {
        return "class JsonSchema {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "}";
    }
}
