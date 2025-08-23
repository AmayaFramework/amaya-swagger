package io.github.amayaframework.swagger;

import java.util.HashMap;
import java.util.Map;

public final class MapEncodingManager implements EncodingManager {
    private final Map<String, Encoder> body;

    public MapEncodingManager(Map<String, Encoder> body) {
        this.body = body;
    }

    public MapEncodingManager() {
        this.body = new HashMap<>();
    }

    @Override
    public Encoder get(String encoding) {
        return body.get(encoding);
    }

    @Override
    public void add(Encoder encoder) {
        body.put(encoder.name(), encoder);
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
