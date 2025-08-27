package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.TaskConsumer;
import io.github.amayaframework.compress.*;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.openui.OpenApiEntry;
import io.github.amayaframework.openui.OpenUi;
import io.github.amayaframework.openui.Part;

import java.util.*;
import java.util.function.Consumer;

public final class SwaggerTaskBuilder extends AbstractSwaggerConfigurer<SwaggerTaskBuilder> {
    private final String defaultRoot;
    private EncodingNegotiatorBuilder builder;

    public SwaggerTaskBuilder(String defaultRoot) {
        this.defaultRoot = defaultRoot;
    }

    @Override
    public void reset() {
        super.reset();
        builder = null;
    }

    @Override
    public EncodingNegotiatorConfigurer negotiatorConfigurer() {
        if (builder == null) {
            builder = new EncodingNegotiatorBuilder();
        }
        return builder;
    }

    @Override
    public SwaggerTaskBuilder configureNegotiator(Consumer<EncodingNegotiatorConfigurer> action) {
        Objects.requireNonNull(action);
        if (builder == null) {
            builder = new EncodingNegotiatorBuilder();
        }
        action.accept(builder);
        return this;
    }

    private EncodingNegotiator buildDefaultNegotiator() {
        var manager = new MapEncodingManager();
        manager.add(IdentityEncoder.INSTANCE);
        manager.add(new GzipEncoder());
        manager.add(new DeflateEncoder());
        return new CachedEncodingNegotiator(
                manager,
                new SplitEncodingHeaderParser(),
                EncodingNegotiatorBuilder.DEFAULT_PRIORITIES,
                EncodingNegotiatorBuilder.DEFAULT_CACHE_LIMIT
        );
    }

    private EncodingNegotiator buildNegotiator() {
        if (negotiator != null) {
            return negotiator;
        }
        if (builder != null) {
            return builder.build();
        }
        return buildDefaultNegotiator();
    }

    private List<OpenApiSource> collectSources() {
        if (documents == null && exposed == null) {
            return null;
        }
        var ret = new LinkedList<OpenApiSource>();
        if (documents != null) {
            ret.addAll(documents);
        }
        if (exposed != null) {
            ret.addAll(exposed.keySet());
        }
        return ret;
    }

    private static OpenApiEntry createEntry(OpenApiSource source, String root) {
        var uri = source.uri();
        var raw = uri.getRawPath();
        var name = source.name();
        if (name == null) {
            name = raw;
        }
        if (root == null || uri.isAbsolute() || raw.charAt(0) == '/') {
            return new OpenApiEntry(uri, name);
        }
        return new OpenApiEntry(PathUtil.normalizeUri(root + '/' + raw), name);
    }

    private OpenUi buildUi(List<OpenApiSource> sources, String root) {
        if (sources == null || sources.isEmpty()) {
            return uiFactory.create();
        }
        if (sources.size() == 1) {
            var first = sources.get(0);
            if (first.name() == null) {
                return uiFactory.create(first.uri());
            }
        }
        return uiFactory.create(sources.stream().map(source -> createEntry(source, root))::iterator);
    }

    private String buildRoot() {
        if (root == null) {
            return defaultRoot;
        }
        return PathUtil.normalize(root);
    }

    private boolean collectExposed(String root, Map<String, Part> parts) {
        Map<String, Part> rooted = null;
        Map<String, Part> standalone = null;
        for (var entry : exposed.entrySet()) {
            var source = entry.getKey();
            var uri = source.uri().getRawPath();
            var part = DocumentPart.of(source, entry.getValue());
            if (uri.charAt(0) == '/') {
                if (standalone == null) {
                    standalone = new HashMap<>();
                }
                standalone.put(PathUtil.normalizePart(uri), part);
            } else {
                if (rooted == null) {
                    rooted = new HashMap<>();
                }
                rooted.put(PathUtil.normalizePart(uri), part);
            }
        }
        if (standalone == null) {
            parts.putAll(rooted);
            return false;
        }
        parts.putAll(standalone);
        if (rooted != null) {
            rooted.forEach((key, value) -> parts.put(root + key, value));
        }
        return true;
    }

    private void buildUiParts(OpenUi ui, String root, Map<String, Part> parts) {
        for (var part : ui.parts()) {
            parts.put(PathUtil.normalize(root + '/' + part.name()), part);
        }
    }

    private void buildUiParts(OpenUi ui, Map<String, Part> parts) {
        for (var part : ui.parts()) {
            parts.put(part.name(), part);
        }
    }

    private TaskConsumer<HttpContext> doBuild() {
        if (uiFactory == null) {
            throw new IllegalStateException("TODO MSG: ui factory должна быть не null");
        }
        var root = buildRoot();
        var parts = new HashMap<String, Part>();
        var hasStandalone = exposed != null && !exposed.isEmpty() && collectExposed(root, parts);
        var ui = buildUi(collectSources(), hasStandalone ? root : null);
        if (hasStandalone) {
            buildUiParts(ui, root, parts);
            return new ExtendedSwaggerTask(parts, root, buildNegotiator());
        }
        buildUiParts(ui, parts);
        return new StandardSwaggerTask(parts, ui.index(), root, buildNegotiator());
    }

    public TaskConsumer<HttpContext> build() {
        try {
            return doBuild();
        } finally {
            reset();
        }
    }
}
