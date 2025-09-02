package io.github.amayaframework.compress;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class MapCompressManagerTest {

    @Test
    public void testAddAndGet() {
        var manager = new MapCompressManager();
        var encoder = IdentityEncoder.INSTANCE;

        manager.add(encoder);
        assertTrue(manager.contains(encoder.name()));
        assertEquals(encoder, manager.get(encoder.name()));
    }

    @Test
    public void testEnsure() {
        var manager = new MapCompressManager();
        var encoder = IdentityEncoder.INSTANCE;

        manager.ensure(encoder);
        assertTrue(manager.contains(encoder.name()));
    }

    @Test
    public void testEnsureWithSupplier() {
        var manager = new MapCompressManager();
        manager.ensure(CompressEncodings.IDENTITY, () -> IdentityEncoder.INSTANCE);

        assertNotNull(manager.get(CompressEncodings.IDENTITY));
    }

    @Test
    public void testRemove() {
        var manager = new MapCompressManager();
        manager.add(IdentityEncoder.INSTANCE);

        var removed = manager.remove(CompressEncodings.IDENTITY);
        assertNotNull(removed);
        assertFalse(manager.contains(CompressEncodings.IDENTITY));
    }

    @Test
    public void testSelect() {
        var manager = new MapCompressManager();
        manager.add(IdentityEncoder.INSTANCE);

        var selected = manager.select(List.of("gzip", "identity"));
        assertEquals(IdentityEncoder.INSTANCE, selected);
    }
}
