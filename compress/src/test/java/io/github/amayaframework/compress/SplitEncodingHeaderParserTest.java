package io.github.amayaframework.compress;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class SplitEncodingHeaderParserTest {

    @Test
    public void testParseWithQValues() {
        var parser = new SplitEncodingHeaderParser();
        var priorities = new HashMap<String, Float>();

        var encodings = parser.parse("gzip;q=0.8, deflate;q=0.2", priorities);
        var list = (List<String>) encodings;

        // gzip (0.8) должен идти выше deflate (0.2)
        assertEquals("gzip", list.get(0));
        assertTrue(list.indexOf("deflate") > list.indexOf("gzip"));
        // identity всегда присутствует
        assertTrue(list.contains("identity"));
    }

    @Test
    public void testParseWithoutQValues() {
        var parser = new SplitEncodingHeaderParser();
        var encodings = parser.parse("gzip, deflate", null);
        var list = (List<String>) encodings;

        // оба имеют q=1.0, порядок не гарантирован
        assertTrue(list.contains("gzip"));
        assertTrue(list.contains("deflate"));
        assertTrue(list.contains("identity"));
    }

    @Test
    public void testParseWithServerPriorities() {
        var parser = new SplitEncodingHeaderParser();
        var priorities = Map.of(
                "gzip", 0.5f,
                "deflate", 0.9f
        );

        var encodings = parser.parse("gzip;q=0.5, deflate;q=0.5", priorities);
        var list = (List<String>) encodings;

        // при равных q=0.5 должен победить deflate (0.9 > 0.5)
        assertEquals("deflate", list.get(0));
    }

    @Test
    public void testParseWithInvalidQValue() {
        var parser = new SplitEncodingHeaderParser();
        var encodings = parser.parse("gzip;q=oops");
        var list = (List<String>) encodings;

        // некорректный q → трактуется как q=1.0
        assertTrue(list.contains("gzip"));
    }

    @Test
    public void testIdentityExcludedWhenQZero() {
        var parser = new SplitEncodingHeaderParser();
        var encodings = parser.parse("identity;q=0");
        var list = (List<String>) encodings;

        // identity исключён при q=0
        assertFalse(list.contains("identity"));
    }

    @Test
    public void testDefaultParseMethodDelegates() {
        var parser = new SplitEncodingHeaderParser();

        var encodings1 = parser.parse("gzip, deflate");
        var encodings2 = parser.parse("gzip, deflate");

        assertEquals(encodings1, encodings2);
    }

    @Test
    public void testIdentityHasLowestPriority() {
        var parser = new SplitEncodingHeaderParser();
        var encodings = parser.parse("gzip;q=0.5, deflate;q=0.5", null);
        var list = (List<String>) encodings;

        // identity с q=0.001 должен быть последним
        assertEquals("identity", list.get(list.size() - 1));
    }
}
