package io.swagger.v3.oas.models;

import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public final class SchemaTests {

    @Test
    public void AdditionalPropertiesBoolean() {
        var schemas = new HashMap<String, Schema>();

        schemas.put("StringSchema", new StringSchema()
                .description("simple string schema")
                .minLength(3)
                .maxLength(100)
                .example("it works")
                .additionalProperties(true)
        );
    }

    @Test
    public void AdditionalPropertiesSchema() {
        var schemas = new HashMap<String, Schema>();

        schemas.put("IntegerSchema", new IntegerSchema()
                .description("simple integer schema")
                .multipleOf(new BigDecimal(3))
                .minimum(new BigDecimal(6))
                .additionalProperties(new StringSchema())
        );
    }

    @Test
    public void AdditionalPropertiesException() {
        Map<String, Schema> schemas = new HashMap<>();
        try {
            schemas.put("IntegerSchema", new IntegerSchema()
                    .description("simple integer schema")
                    .multipleOf(new BigDecimal(3))
                    .minimum(new BigDecimal(6))
                    .additionalProperties("ok")
            );
            fail("Should have thrown an exception");
        } catch (Exception ignored) {
        }
    }
}
