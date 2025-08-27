package io.github.amayaframework.compress;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * TODO
 */
public final class MapEncodingManager implements EncodingManager {
    private final Map<String, Encoder> body;

    /**
     * TODO
     * @param body
     */
    public MapEncodingManager(Map<String, Encoder> body) {
        this.body = body;
    }

    /**
     * TODO
     */
    public MapEncodingManager() {
        this.body = new HashMap<>();
    }

    @Override
    public Encoder get(String encoding) {
        return body.get(encoding);
    }

    @Override
    public boolean contains(String encoding) {
        return body.containsKey(encoding);
    }

    @Override
    public void add(Encoder encoder) {
        if (encoder != null) {
            body.put(encoder.name(), encoder);
        }
    }

    @Override
    public void ensure(Encoder encoder) {
        if (encoder != null) {
            body.putIfAbsent(encoder.name(), encoder);
        }
    }

    @Override
    public void ensure(String name, Supplier<Encoder> supplier) {
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
    public Encoder remove(String encoding) {
        return body.remove(encoding);
    }

    @Override
    public Encoder select(Iterable<String> encodings) {
        for (var encoding : encodings) {
            var found = body.get(encoding);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
