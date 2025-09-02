package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.Task;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.context.HttpRequest;
import io.github.amayaframework.context.HttpResponse;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.openui.Part;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class Util {
    private Util() {
    }

    static Task<HttpContext> failTask() {
        return FailTask.INSTANCE;
    }

    static Part part(String name, String body) {
        return new MockPart(name, body);
    }

    static Part part(String name) {
        return new MockPart(name, "");
    }

    static HttpContext context(String path) throws Exception {
        var req = mock(HttpRequest.class);
        when(req.path()).thenReturn(path);
        when(req.header("Accept-Encoding")).thenReturn(null);

        var res = mock(HttpResponse.class);
        when(res.outputStream()).thenReturn(mock(ServletOutputStream.class));
        when(res.httpVersion()).thenReturn(HttpVersion.HTTP_1_1);

        var ctx = mock(HttpContext.class);
        when(ctx.request()).thenReturn(req);
        when(ctx.response()).thenReturn(res);
        return ctx;
    }

    static ServletOutputStream wrap(ByteArrayOutputStream baos) {
        return new ServletOutputStream() {

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }

            @Override
            public void write(int b) {
                baos.write(b);
            }
        };
    }

    static final class MockPart implements Part {
        final String name;
        final String body;

        MockPart(String name, String body) {
            this.name = name;
            this.body = body;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public MimeType mimeType() {
            return MimeType.HTML;
        }

        @Override
        public String charset() {
            return "utf-8";
        }

        @Override
        public java.io.InputStream inputStream() {
            return new ByteArrayInputStream(body.getBytes());
        }
    }

    static final class FailTask implements Task<HttpContext> {
        static final FailTask INSTANCE = new FailTask();

        @Override
        public void run(HttpContext context) {
            fail("Should not delegate");
        }

        @Override
        public CompletableFuture<Void> runAsync(HttpContext context) {
            fail("Should not delegate");
            return null;
        }
    }
}
