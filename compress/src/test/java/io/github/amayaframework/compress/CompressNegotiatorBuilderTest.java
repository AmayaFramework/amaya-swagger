package io.github.amayaframework.compress;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class CompressNegotiatorBuilderTest {

    @Test
    public void testBuildWithDefaults() {
        var builder = new CompressNegotiatorBuilder();
        var negotiator = builder.build();

        var encoder = negotiator.negotiate("gzip, identity");
        assertEquals("gzip", encoder.name());
    }

    @Test
    public void testCustomCacheLimit() {
        var builder = new CompressNegotiatorBuilder().cacheLimit(1);
        var negotiator = builder.build();

        assertNotNull(negotiator.negotiate("identity"));
    }

    @Test
    public void testAddCustomEncoder() {
        var builder = new CompressNegotiatorBuilder();
        builder.addEncoder(IdentityEncoder.INSTANCE, 0.5f);

        var negotiator = builder.build();
        var encoder = negotiator.negotiate("identity");
        assertEquals(IdentityEncoder.INSTANCE, encoder);
    }
}
