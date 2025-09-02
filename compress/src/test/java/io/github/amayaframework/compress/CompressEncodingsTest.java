package io.github.amayaframework.compress;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class CompressEncodingsTest {

    @Test
    public void testConstants() {
        assertEquals("identity", CompressEncodings.IDENTITY);
        assertEquals("gzip", CompressEncodings.GZIP);
        assertEquals("deflate", CompressEncodings.DEFLATE);
        assertEquals("br", CompressEncodings.BROTLI);
    }
}
