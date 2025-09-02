package io.github.amayaframework.compress;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Base implementation of {@link CompressNegotiatorConfigurer}.
 * <p>
 * Provides default storage and fluent methods for configuring
 * an encoding parser, manager, encoders, and priorities.
 * Subclasses can extend this class to build concrete negotiator
 * configurers with additional behavior (e.g. producing a specific
 * {@link CompressNegotiator}).
 *
 * @param <C> the self type, enabling fluent method chaining
 */
public abstract class AbstractCompressNegotiatorConfigurer<C extends CompressNegotiatorConfigurer> implements CompressNegotiatorConfigurer {
    protected EncodingHeaderParser parser;
    protected CompressManager manager;
    protected Map<String, CompressEncoder> encoders;
    protected Collection<CompressEncoder> encodersView;
    protected Map<String, Float> priorities;

    /**
     * Ensures that the encoder registry is initialized.
     * Lazily creates a backing map and unmodifiable view if needed.
     */
    protected void ensureEncoders() {
        if (encoders == null) {
            encoders = new HashMap<>();
            encodersView = Collections.unmodifiableCollection(encoders.values());
        }
    }

    /**
     * Ensures that the priorities map is initialized.
     * Lazily creates it if needed.
     */
    protected void ensurePriorities() {
        if (priorities == null) {
            priorities = new HashMap<>();
        }
    }

    /**
     * Sets the priority (q-value) for the given encoding.
     * <p>
     * If both {@code priorities} and {@code priority} are {@code null},
     * nothing is changed.
     *
     * @param encoding the encoding name
     * @param priority the priority value, may be {@code null}
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
