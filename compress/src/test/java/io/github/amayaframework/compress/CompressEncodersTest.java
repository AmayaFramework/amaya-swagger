package io.github.amayaframework.compress;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import static org.junit.jupiter.api.Assertions.*;

public final class CompressEncodersTest {

    @Test
    public void testIdentityEncoder() throws IOException {
        var encoder = IdentityEncoder.INSTANCE;
        assertEquals(CompressEncodings.IDENTITY, encoder.name());

        var output = new ByteArrayOutputStream();
        var stream = encoder.encode(output);
        stream.write("test".getBytes());
        stream.flush();
        assertEquals("test", output.toString());
    }

    @Test
    public void testGzipEncoder() throws IOException {
        var encoder = new GzipEncoder();
        assertEquals(CompressEncodings.GZIP, encoder.name());

        var output = new ByteArrayOutputStream();
        try (var encoded = encoder.encode(output)) {
            encoded.write("hello".getBytes());
        }
        try (var in = new GZIPInputStream(new java.io.ByteArrayInputStream(output.toByteArray()))) {
            assertEquals("hello", new String(in.readAllBytes()));
        }
    }

    @Test
    public void testDeflateEncoder() throws IOException {
        var encoder = new DeflateEncoder();
        assertEquals(CompressEncodings.DEFLATE, encoder.name());

        var output = new ByteArrayOutputStream();
        try (var encoded = encoder.encode(output)) {
            encoded.write("data".getBytes());
        }
        try (var in = new InflaterInputStream(new java.io.ByteArrayInputStream(output.toByteArray()), new Inflater(true))) {
            assertEquals("data", new String(in.readAllBytes()));
        }
    }
}
