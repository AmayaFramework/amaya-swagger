package io.github.amayaframework.swagger;

import io.github.amayaframework.compress.CompressNegotiator;
import io.github.amayaframework.openui.OpenApiEntry;
import io.github.amayaframework.openui.OpenUi;
import io.github.amayaframework.openui.OpenUiFactory;
import io.github.amayaframework.openui.Part;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public final class SwaggerTaskBuilderTest {

    @Test
    public void testBuildWithoutUiFactoryThrows() {
        var builder = new SwaggerTaskBuilder("/swagger");
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    public void testBuildWithEmptyUiAndNoDocs() {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create()).thenReturn(ui);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        var task = builder.build();
        assertInstanceOf(StandardSwaggerTask.class, task);
    }

    @Test
    public void testSingleUnnamedDocument() {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));

        var source = mock(OpenApiSource.class);
        when(source.uri()).thenReturn(URI.create("openapi.yaml"));
        when(source.name()).thenReturn(null);

        when(uiFactory.create(source.uri())).thenReturn(ui);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.addDocument(source);

        var task = builder.build();
        assertInstanceOf(StandardSwaggerTask.class, task);
        verify(uiFactory).create(source.uri());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMultipleDocuments() {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));

        var src1 = mock(OpenApiSource.class);
        when(src1.uri()).thenReturn(URI.create("api1.yaml"));
        when(src1.name()).thenReturn("api1");

        var src2 = mock(OpenApiSource.class);
        when(src2.uri()).thenReturn(URI.create("api2.yaml"));
        when(src2.name()).thenReturn("api2");

        when(uiFactory.create((Iterable<OpenApiEntry>) any())).thenReturn(ui);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.addDocuments(src1, src2);

        var task = builder.build();
        assertInstanceOf(StandardSwaggerTask.class, task);
        verify(uiFactory).create(any(Iterable.class));
    }

    @Test
    public void testExposeRelativeDocumentStandard() {
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        var uiFactory = mock(OpenUiFactory.class);
        when(uiFactory.create(any(Iterable.class))).thenReturn(ui);

        var source = mock(OpenApiSource.class);
        when(source.uri()).thenReturn(URI.create("api.json"));
        when(source.name()).thenReturn("API");
        when(source.format()).thenReturn(OpenApiFormat.JSON);
        when(source.provider()).thenReturn(() -> null);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.exposeDocument(source);

        var task = builder.build();
        assertInstanceOf(StandardSwaggerTask.class, task);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testExposeAbsoluteDocumentExtended() {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create(any(Iterable.class))).thenReturn(ui);

        var source = mock(OpenApiSource.class);
        when(source.uri()).thenReturn(URI.create("/openapi.json"));
        when(source.name()).thenReturn("API");
        when(source.format()).thenReturn(OpenApiFormat.JSON);
        when(source.provider()).thenReturn(() -> null);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.exposeDocument(source);

        var task = builder.build();
        assertInstanceOf(ExtendedSwaggerTask.class, task);
    }

    @Test
    public void testRootHandling() {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create()).thenReturn(ui);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);

        builder.root(URI.create("custom"));
        var task = builder.build();
        assertNotNull(task);

        assertThrows(IllegalArgumentException.class, () -> builder.root(URI.create("http://absolute")));
    }

    @Test
    public void testNegotiatorDirect() {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create()).thenReturn(ui);

        var negotiator = mock(CompressNegotiator.class);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory).negotiator(negotiator);
        var task = builder.build();
        assertNotNull(task);
    }

    @Test
    public void testNegotiatorConfigurer() {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create()).thenReturn(ui);

        var flag = new AtomicBoolean(false);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.negotiator(cfg -> {
            flag.set(true);
        });
        var task = builder.build();

        assertTrue(flag.get());
        assertNotNull(task);
    }

    @Test
    public void testResetAfterBuild() {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create()).thenReturn(ui);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.build();

        assertNull(builder.uiFactory());
        assertNull(builder.negotiator());
    }

    @Test
    public void testEmptyDocumentsCallsCreateNoArgs() {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create()).thenReturn(ui);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);

        var task = builder.build();
        assertInstanceOf(StandardSwaggerTask.class, task);
        // Проверяем, что вызвался именно create() без аргументов
        verify(uiFactory).create();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRelativeAndAbsoluteUriHandling() throws Exception {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create((Iterable<OpenApiEntry>) any())).thenReturn(ui);

        // относительный документ (только для UI)
        var relative = mock(OpenApiSource.class);
        when(relative.uri()).thenReturn(URI.create("openapi.json"));
        when(relative.name()).thenReturn("relative");

        // абсолютный документ (для UI и для parts)
        var absolute = mock(OpenApiSource.class);
        when(absolute.uri()).thenReturn(URI.create("/openapi.json"));
        when(absolute.name()).thenReturn("absolute");
        when(absolute.format()).thenReturn(OpenApiFormat.JSON);
        when(absolute.provider()).thenReturn(() -> null);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.addDocument(relative).exposeDocument(absolute);

        var task = builder.build();
        assertInstanceOf(ExtendedSwaggerTask.class, task);

        // проверяем, что в parts есть только абсолютный
        var field = task.getClass().getDeclaredField("parts");
        field.setAccessible(true);
        var parts = (Map<String, Part>) field.get(task);
        assertTrue(parts.containsKey("/openapi.json"));
        assertFalse(parts.containsKey("/swagger/openapi.json"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStandardTaskUriNormalization() throws Exception {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create(any(Iterable.class))).thenReturn(ui);

        var src1 = mock(OpenApiSource.class);
        when(src1.uri()).thenReturn(URI.create("segment/file.ext"));
        when(src1.name()).thenReturn("s1");
        when(src1.format()).thenReturn(OpenApiFormat.JSON);
        when(src1.provider()).thenReturn(() -> null);

        var src2 = mock(OpenApiSource.class);
        when(src2.uri()).thenReturn(URI.create("file.ext"));
        when(src2.name()).thenReturn("s2");
        when(src2.format()).thenReturn(OpenApiFormat.JSON);
        when(src2.provider()).thenReturn(() -> null);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.exposeDocuments(src1, src2);

        var task = builder.build();
        assertInstanceOf(StandardSwaggerTask.class, task);

        var field = task.getClass().getDeclaredField("parts");
        field.setAccessible(true);
        var parts = (Map<String, Part>) field.get(task);

        assertTrue(parts.containsKey("segment/file.ext"), "Expected normalized segment/file.ext");
        assertTrue(parts.containsKey("file.ext"), "Expected normalized file.ext");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testExtendedTaskUriNormalization() throws Exception {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create(any(Iterable.class))).thenReturn(ui);

        var src1 = mock(OpenApiSource.class);
        when(src1.uri()).thenReturn(URI.create("/segment/file.ext"));
        when(src1.name()).thenReturn("s1");
        when(src1.format()).thenReturn(OpenApiFormat.JSON);
        when(src1.provider()).thenReturn(() -> null);

        var src2 = mock(OpenApiSource.class);
        when(src2.uri()).thenReturn(URI.create("/file.ext"));
        when(src2.name()).thenReturn("s2");
        when(src2.format()).thenReturn(OpenApiFormat.JSON);
        when(src2.provider()).thenReturn(() -> null);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.exposeDocuments(src1, src2);

        var task = builder.build();
        assertInstanceOf(ExtendedSwaggerTask.class, task);

        var field = task.getClass().getDeclaredField("parts");
        field.setAccessible(true);
        var parts = (Map<String, Part>) field.get(task);

        assertTrue(parts.containsKey("/segment/file.ext"), "Expected normalized /segment/file.ext");
        assertTrue(parts.containsKey("/file.ext"), "Expected normalized /file.ext");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRootPrefixedAbsoluteIsTreatedAsRelative() throws Exception {
        var uiFactory = mock(OpenUiFactory.class);
        var ui = mock(OpenUi.class);
        when(ui.parts()).thenReturn(List.of(Util.part("index.html")));
        when(ui.index()).thenReturn(Util.part("index.html"));
        when(uiFactory.create(any(Iterable.class))).thenReturn(ui);

        // Источник с URI, начинающимся с root
        var src = mock(OpenApiSource.class);
        when(src.uri()).thenReturn(URI.create("/swagger/openapi.json"));
        when(src.name()).thenReturn("api");
        when(src.format()).thenReturn(OpenApiFormat.JSON);
        when(src.provider()).thenReturn(() -> null);

        var builder = new SwaggerTaskBuilder("/swagger").uiFactory(uiFactory);
        builder.exposeDocument(src);

        var task = builder.build();
        assertInstanceOf(StandardSwaggerTask.class, task,
                "Root-prefixed absolute should be treated as relative → StandardSwaggerTask");

        var field = task.getClass().getDeclaredField("parts");
        field.setAccessible(true);
        var parts = (Map<String, Part>) field.get(task);

        assertTrue(parts.containsKey("openapi.json"),
                "Expected root prefix to be stripped → key 'openapi.json'");
        assertFalse(parts.containsKey("/swagger/openapi.json"),
                "Should not keep full prefixed absolute path");
    }
}
