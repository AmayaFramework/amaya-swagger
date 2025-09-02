package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.SyncTask;
import io.github.amayaframework.compress.IdentityEncoder;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.context.HttpRequest;
import io.github.amayaframework.context.HttpResponse;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpVersion;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public final class StandardSwaggerTaskTest {

    @Test
    public void testRedirectFromRoot() throws Throwable {
        var index = Util.part("index.html", "INDEX");
        var task = new StandardSwaggerTask(Map.of(), index, "/swagger", h -> IdentityEncoder.INSTANCE);

        var ctx = Util.context("/swagger");
        task.run(ctx, Util.failTask());
        verify(ctx.response()).status(HttpCode.PERMANENT_REDIRECT);
    }

    @Test
    public void testServeIndex() throws Throwable {
        var index = Util.part("index.html", "INDEX");
        var task = new StandardSwaggerTask(Map.of(), index, "/swagger", h -> IdentityEncoder.INSTANCE);

        var ctx = Util.context("/swagger/");
        task.run(ctx, Util.failTask());
        verify(ctx.response()).mimeData(any());
    }

    @Test
    public void testServeFile() throws Throwable {
        var index = Util.part("index.html", "INDEX");
        var js = Util.part("file.js", "JS");
        var task = new StandardSwaggerTask(Map.of("file.js", js), index, "/swagger", h -> IdentityEncoder.INSTANCE);

        var ctx = Util.context("/swagger/file.js");
        task.run(ctx, Util.failTask());
        verify(ctx.response()).mimeData(any());
    }

    @Test
    public void testNotFoundFile() throws Throwable {
        var index = Util.part("index.html", "INDEX");
        var task = new StandardSwaggerTask(Map.of(), index, "/swagger", h -> IdentityEncoder.INSTANCE);

        var ctx = Util.context("/swagger/missing.js");
        task.run(ctx, Util.failTask());
        verify(ctx.response()).sendError(HttpCode.NOT_FOUND);
    }

    @Test
    public void testDelegateOutsideRoot() throws Throwable {
        var index = Util.part("index.html", "INDEX");
        var task = new StandardSwaggerTask(Map.of(), index, "/swagger", h -> IdentityEncoder.INSTANCE);

        var ctx = Util.context("/api");
        var called = new boolean[1];
        task.run(ctx, (SyncTask<HttpContext>) c -> called[0] = true);
        assertTrue(called[0]);
    }

    @Test
    public void testRunAsyncServeIndex() throws Exception {
        var index = Util.part("index.html", "INDEX");
        var task = new StandardSwaggerTask(Map.of(), index, "/swagger", h -> IdentityEncoder.INSTANCE);

        var req = mock(HttpRequest.class);
        when(req.path()).thenReturn("/swagger/");
        when(req.header("Accept-Encoding")).thenReturn(null);

        var baos = new ByteArrayOutputStream();

        var res = mock(HttpResponse.class);
        when(res.outputStream()).thenReturn(Util.wrap(baos));
        when(res.httpVersion()).thenReturn(HttpVersion.HTTP_1_1);

        var ctx = mock(HttpContext.class);
        when(ctx.request()).thenReturn(req);
        when(ctx.response()).thenReturn(res);

        var f = task.runAsync(ctx, Util.failTask());
        f.join(); // дождаться завершения

        verify(res).mimeData(any());
        var result = baos.toString(StandardCharsets.UTF_8);
        assertEquals("INDEX", result);
    }
}
