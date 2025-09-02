package io.github.amayaframework.compress;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class CachedCompressNegotiatorTest {

    @Test
    public void testNegotiationWithQValues() {
        var manager = new MapCompressManager();
        manager.add(IdentityEncoder.INSTANCE);
        manager.add(new GzipEncoder());
        manager.add(new DeflateEncoder());

        var negotiator = new CachedCompressNegotiator(
                manager,
                new SplitEncodingHeaderParser(),
                Map.of(), // без серверных приоритетов
                10
        );

        var encoder = negotiator.negotiate("gzip;q=0.8, deflate;q=0.2");
        assertEquals("gzip", encoder.name());
    }

    @Test
    public void testNegotiationWithServerPriorities() {
        var manager = new MapCompressManager();
        manager.add(new GzipEncoder());
        manager.add(new DeflateEncoder());

        var priorities = Map.of(
                "gzip", 0.5f,
                "deflate", 0.9f
        );

        var negotiator = new CachedCompressNegotiator(
                manager,
                new SplitEncodingHeaderParser(),
                priorities,
                10
        );

        // Оба с q=0.5 → победит deflate по серверным приоритетам
        var encoder = negotiator.negotiate("gzip;q=0.5, deflate;q=0.5");
        assertEquals("deflate", encoder.name());
    }

    @Test
    public void testCacheBehavior() {
        var manager = new MapCompressManager();
        manager.add(new GzipEncoder());

        var negotiator = new CachedCompressNegotiator(
                manager,
                new SplitEncodingHeaderParser(),
                Map.of(),
                2 // ограничение размера кэша
        );

        // Первый вызов — закэшируется
        var encoder1 = negotiator.negotiate("gzip");
        assertEquals("gzip", encoder1.name());

        // Повторный вызов — берётся из кэша
        var encoder2 = negotiator.negotiate("gzip");
        assertSame(encoder1, encoder2);
    }

    @Test
    public void testCacheDisabled() {
        var manager = new MapCompressManager();
        manager.add(new GzipEncoder());

        var negotiator = new CachedCompressNegotiator(
                manager,
                new SplitEncodingHeaderParser(),
                Map.of(),
                0 // отключаем кэш
        );

        var encoder1 = negotiator.negotiate("gzip");
        var encoder2 = negotiator.negotiate("gzip");

        // Энкодеры равны по name, но ссылки могут различаться
        assertEquals(encoder1.name(), encoder2.name());
    }

    @Test
    public void testResetClearsCache() {
        var manager = new MapCompressManager();
        manager.add(new GzipEncoder());

        var negotiator = new CachedCompressNegotiator(
                manager,
                new SplitEncodingHeaderParser(),
                Map.of(),
                10
        );

        var encoder1 = negotiator.negotiate("gzip");
        negotiator.reset();
        var encoder2 = negotiator.negotiate("gzip");

        // После reset ссылка может измениться
        assertEquals(encoder1.name(), encoder2.name());
    }

    @Test
    public void testNullOrEmptyHeaderReturnsIdentity() {
        var manager = new MapCompressManager();
        manager.add(IdentityEncoder.INSTANCE);

        var negotiator = new CachedCompressNegotiator(
                manager,
                new SplitEncodingHeaderParser(),
                Map.of(),
                10
        );

        assertEquals("identity", negotiator.negotiate(null).name());
        assertEquals("identity", negotiator.negotiate("").name());
    }
}
