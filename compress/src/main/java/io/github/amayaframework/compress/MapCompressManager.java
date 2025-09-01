package io.github.amayaframework.compress;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * TODO
 */
public final class MapCompressManager implements CompressManager {
    private final Map<String, CompressEncoder> body;

    /**
     * TODO
     * @param body
     */
    public MapCompressManager(Map<String, CompressEncoder> body) {
        this.body = body;
    }

    /**
     * TODO
     */
    public MapCompressManager() {
        this.body = new HashMap<>();
    }

    @Override
    public CompressEncoder get(String encoding) {
        return body.get(encoding);
    }

    @Override
    public boolean contains(String encoding) {
        return body.containsKey(encoding);
    }

    @Override
    public void add(CompressEncoder encoder) {
        if (encoder != null) {
            body.put(encoder.name(), encoder);
        }
    }

    @Override
    public void ensure(CompressEncoder encoder) {
        if (encoder != null) {
            body.putIfAbsent(encoder.name(), encoder);
        }
    }

    @Override
    public void ensure(String name, Supplier<CompressEncoder> supplier) {
        if (name == null || supplier == null) {
            return;
        }
        if (body.containsKey(name)) {
            return;
        }
        var encoder = supplier.get();
        if (encoder != null) {
            body.put(name, encoder);
        }
    }

    @Override
    public CompressEncoder remove(String encoding) {
        return body.remove(encoding);
    }

    @Override
    public CompressEncoder select(Iterable<String> encodings) {
        for (var encoding : encodings) {
            var found = body.get(encoding);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
