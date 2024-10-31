package io.swagger.v3.oas.models;

import io.swagger.v3.oas.models.links.LinkParameter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class LinkParameterTest {

    @Test
    public void testValue() {
        LinkParameter linkParameter = new LinkParameter();
        linkParameter.setValue("foo");
        linkParameter.setValue("bar");
        linkParameter.setValue("baz");

        assertEquals(linkParameter.value("bar"), linkParameter);
        assertEquals(linkParameter.getValue(), "bar");
    }

    @Test
    public void testEquals() {
        var linkParameter = new LinkParameter();
        assertFalse(linkParameter.equals(null));
        assertFalse(linkParameter.equals(""));

        assertTrue(linkParameter.equals(linkParameter));
        assertTrue(linkParameter.equals(new LinkParameter()));
    }

    @Test
    public void testGetExtensions1() {
        LinkParameter linkParameter = new LinkParameter();
        linkParameter.addExtension("", null);
        linkParameter.addExtension("y-", null);
        linkParameter.addExtension(null, null);

        assertNull(linkParameter.getExtensions());
    }

    @Test
    public void testGetExtensions2() {
        LinkParameter linkParameter = new LinkParameter();
        linkParameter.addExtension("x-", "foo");
        linkParameter.addExtension("x-", "bar");
        linkParameter.addExtension("x-", "baz");
        assertEquals(linkParameter.getExtensions(), Map.of("x-", "baz"));
    }

    @Test
    public void testGetExtensions3() {
        var linkParameter = new LinkParameter();
        var hashMap = new HashMap<String, Object>();
        hashMap.put("x-", "foo");
        hashMap.put("x-", "bar");
        hashMap.put("x-", "baz");
        linkParameter.setExtensions(hashMap);
        assertEquals(linkParameter.getExtensions(), Map.of("x-", "baz"));
    }

    @Test
    public void testExtensions() {
        var linkParameter = new LinkParameter();
        var hashMap = new HashMap<String, Object>();
        hashMap.put("x-", "foo");
        hashMap.put("x-", "bar");
        hashMap.put("x-", "baz");
        assertEquals(linkParameter.extensions(hashMap), linkParameter);
    }

    @Test
    public void testToString() {
        var linkParameter = new LinkParameter();
        linkParameter.setValue("foo");
        assertEquals(linkParameter.toString(), "class LinkParameter {\n}");
    }
}
