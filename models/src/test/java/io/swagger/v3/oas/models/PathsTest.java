package io.swagger.v3.oas.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class PathsTest {

    @Test
    public void testAddPathItem() {
        Paths paths = new Paths();
        assertEquals(paths.addPathItem("foo", null), paths);
    }

    @Test
    public void testEquals() {
        var paths = new Paths();
        assertTrue(paths.equals(paths));
        assertTrue(paths.equals(new Paths()));

        assertFalse(paths.equals(null));
        assertFalse(paths.equals(new String()));
    }

    @Test
    public void testGetExtensions1() {
        var paths = new Paths();
        paths.addExtension("", null);
        paths.addExtension("y-", null);
        paths.addExtension(null, null);

        assertNull(paths.getExtensions());
    }

    @Test
    public void testGetExtensions2() {
        var paths = new Paths();
        paths.addExtension("x-", "foo");
        paths.addExtension("x-", "bar");
        paths.addExtension("x-", "baz");

        assertEquals(paths.getExtensions(),
                new HashMap<String, Object>() {{
                    put("x-", "baz");
                }});
    }

    @Test
    public void testGetExtensions3() {
        var paths = new Paths();
        var hashMap = new HashMap<String, Object>();
        hashMap.put("x-", "foo");
        hashMap.put("x-", "bar");
        hashMap.put("x-", "baz");
        paths.setExtensions(hashMap);

        assertEquals(paths.getExtensions(), Map.of("x-", "baz"));
    }

    @Test
    public void testExtensions() {
        Paths paths = new Paths();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("x-", "foo");
        hashMap.put("x-", "bar");
        hashMap.put("x-", "baz");

        assertEquals(paths.extensions(hashMap), paths);
    }

    @Test
    public void testToString() {
        Paths paths = new Paths();
        paths.addPathItem("foo", null);
        assertEquals(paths.toString(), "class Paths {\n    {foo=null}\n}");
    }
}
