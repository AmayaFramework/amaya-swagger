package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.TaskConsumer;
import io.github.amayaframework.compress.*;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.openui.OpenApiEntry;
import io.github.amayaframework.openui.OpenUi;
import io.github.amayaframework.openui.Part;

import java.util.*;
import java.util.function.Consumer;

/**
 * Builder for creating Swagger tasks that serve Swagger UI
 * and optionally expose OpenAPI documents.
 * <p>
 * This class extends {@link AbstractSwaggerConfigurer} to provide
 * fluent configuration of negotiators, UI factory, root path, and
 * OpenAPI sources. The resulting task can be used as a
 * {@link TaskConsumer} in an application pipeline.
 * <p>
 * Key behaviors:
 * <ul>
 *   <li>If no {@link CompressNegotiator} is provided, a default one is built
 *   using {@link GzipEncoder}, {@link DeflateEncoder}, and {@link IdentityEncoder}.</li>
 *   <li>If sources are added via {@code add*}, they appear in Swagger UI only.</li>
 *   <li>If sources are added via {@code expose*}, they are visible in Swagger UI
 *   and also directly served by the module.</li>
 *   <li>If any exposed source uses an absolute path (starting with {@code /}),
 *   {@link ExtendedSwaggerTask} is used. Otherwise, {@link StandardSwaggerTask} is used.</li>
 * </ul>
 */
public final class SwaggerTaskBuilder extends AbstractSwaggerConfigurer<SwaggerTaskBuilder> {
    private final String defaultRoot;
    private CompressNegotiatorBuilder builder;

    /**
     * Creates a new builder with the given default root path.
     *
     * @param defaultRoot the default root path under which Swagger is served
     */
    public SwaggerTaskBuilder(String defaultRoot) {
        this.defaultRoot = defaultRoot;
    }

    @Override
    public void reset() {
        super.reset();
        builder = null;
    }

    @Override
    public CompressNegotiatorConfigurer negotiatorConfigurer() {
        if (builder == null) {
            builder = new CompressNegotiatorBuilder();
        }
        return builder;
    }

    @Override
    public SwaggerTaskBuilder configureNegotiator(Consumer<CompressNegotiatorConfigurer> action) {
        Objects.requireNonNull(action, "Configurator action must not be null");
        if (builder == null) {
            builder = new CompressNegotiatorBuilder();
        }
        action.accept(builder);
        return this;
    }

    private CompressNegotiator buildDefaultNegotiator() {
        var manager = new MapCompressManager();
        manager.add(IdentityEncoder.INSTANCE);
        manager.add(new GzipEncoder());
        manager.add(new DeflateEncoder());
        return new CachedCompressNegotiator(
                manager,
                new SplitEncodingHeaderParser(),
                CompressNegotiatorBuilder.DEFAULT_PRIORITIES,
                CompressNegotiatorBuilder.DEFAULT_CACHE_LIMIT
        );
    }

    private CompressNegotiator buildNegotiator() {
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
            ret.addAll(exposed);
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
        for (var source : exposed) {
            var uri = source.uri().getRawPath();
            var part = DocumentPart.of(source);
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
            throw new IllegalStateException("UI factory must not be null");
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

    /**
     * Builds a new {@link TaskConsumer} for serving Swagger UI and documents,
     * based on the current configuration.
     * <p>
     * After building, this builder is reset to its initial state.
     *
     * @return a new Swagger {@link TaskConsumer}
     * @throws IllegalStateException if required properties (such as UI factory) are missing
     */
    public TaskConsumer<HttpContext> build() {
        try {
            return doBuild();
        } finally {
            reset();
        }
    }
}
