package io.github.amayaframework.compress;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * @param <C>
 */
public abstract class AbstractCompressNegotiatorConfigurer<C extends CompressNegotiatorConfigurer> implements CompressNegotiatorConfigurer {
    protected EncodingHeaderParser parser;
    protected CompressManager manager;
    protected Map<String, CompressEncoder> encoders;
    protected Collection<CompressEncoder> encodersView;
    protected Map<String, Float> priorities;

    /**
     * TODO
     */
    protected void ensureEncoders() {
        if (encoders == null) {
            encoders = new HashMap<>();
            encodersView = Collections.unmodifiableCollection(encoders.values());
        }
    }

    /**
     * TODO
     */
    protected void ensurePriorities() {
        if (priorities == null) {
            priorities = new HashMap<>();
        }
    }

    /**
     * TODO
     * @param encoding
     * @param priority
     */
    protected void setPriority(String encoding, Float priority) {
        if (priorities == null && priority == null) {
            return;
        }
        ensurePriorities();
        priorities.put(encoding, priority);
    }

    @Override
    public void reset() {
        parser = null;
        manager = null;
        encoders = null;
        encodersView = null;
        priorities = null;
    }

    @Override
    public EncodingHeaderParser parser() {
        return parser;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C parser(EncodingHeaderParser parser) {
        this.parser = parser;
        return (C) this;
    }

    @Override
    public CompressManager manager() {
        return manager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C manager(CompressManager manager) {
        this.manager = manager;
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<CompressEncoder> encoders() {
        return encodersView == null ? Collections.EMPTY_LIST : encodersView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C addEncoder(CompressEncoder encoder, Float priority) {
        ensureEncoders();
        var name = encoder.name();
        encoders.put(name, encoder);
        setPriority(name, priority);
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C removeEncoder(String name) {
        if (encoders != null) {
            encoders.remove(name);
            if (priorities != null) {
                priorities.remove(name);
            }
        }
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C addEncoder(CompressEncoder encoder) {
        ensureEncoders();
        encoders.put(encoder.name(), encoder);
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Float> priorities() {
        return priorities == null ? Collections.EMPTY_MAP : priorities;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C priorities(Map<String, Float> priorities) {
        this.priorities = priorities;
        return (C) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public C priority(String encoding, Float value) {
        setPriority(encoding, value);
        return (C) this;
    }
}
