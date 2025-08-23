package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskConsumer;
import com.github.romanqed.jsync.Futures;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.context.HttpRequest;
import io.github.amayaframework.context.HttpResponse;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.HttpVersion;
import io.github.amayaframework.http.MimeData;
import io.github.amayaframework.openui.Part;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractSwaggerTask implements TaskConsumer<HttpContext> {
    protected static final String ACCEPT_ENCODING = "Accept-Encoding";
    protected static final String CONTENT_ENCODING = "Content-Encoding";
    protected static final String LOCATION = "Location";
    protected final EncodingNegotiator negotiator;

    protected AbstractSwaggerTask(EncodingNegotiator negotiator) {
        this.negotiator = negotiator;
    }

    protected static MimeData getMimeData(Part part) {
        var type = part.mimeType();
        var data = new MimeData(type);
        if (type.isText()) {
            data.setParameter("charset");
            data.setValue(part.charset());
        }
        return data;
    }

    protected static void sendPart(HttpResponse res, Encoder encoder, Part part) throws IOException {
        res.mimeData(getMimeData(part));
        OutputStream outputStream = res.outputStream();
        if (encoder != null) {
            res.header(CONTENT_ENCODING, encoder.name());
            outputStream = encoder.encode(outputStream);
        }
        try (var inputStream = part.inputStream()) {
            inputStream.transferTo(outputStream);
        }
    }

    protected void sendPart(HttpRequest req, HttpResponse res, Part part) throws IOException {
        var encoder = negotiator.negotiate(req.header(ACCEPT_ENCODING));
        if (encoder == null) {
            res.status(HttpCode.NOT_ACCEPTABLE);
            res.contentLength(0);
            return;
        }
        sendPart(res, encoder == IdentityEncoder.INSTANCE ? null : encoder, part);
    }

    protected static CompletableFuture<Void> sendPartAsync(OutputStream stream, Part part) {
        return Futures.run(() -> {
            try (var inputStream = part.inputStream()) {
                inputStream.transferTo(stream);
            }
        });
    }

    protected static CompletableFuture<Void> sendPartAsync(HttpResponse res, Encoder encoder, Part part) throws IOException {
        res.mimeData(getMimeData(part));
        OutputStream outputStream = res.outputStream();
        if (encoder != null) {
            res.header(CONTENT_ENCODING, encoder.name());
            outputStream = encoder.encode(outputStream);
        }
        return sendPartAsync(outputStream, part);
    }

    protected CompletableFuture<Void> sendPartAsync(HttpRequest req, HttpResponse res, Part part) throws IOException {
        var encoder = negotiator.negotiate(req.header(ACCEPT_ENCODING));
        if (encoder == null) {
            res.status(HttpCode.NOT_ACCEPTABLE);
            res.contentLength(0);
            return CompletableFuture.completedFuture(null);
        }
        return sendPartAsync(res, encoder == IdentityEncoder.INSTANCE ? null : encoder, part);
    }

    protected static void redirect(HttpResponse res, String url) {
        if (res.httpVersion().before(HttpVersion.HTTP_1_1)) {
            res.status(HttpCode.MOVED_PERMANENTLY);
        } else {
            res.status(HttpCode.PERMANENT_REDIRECT);
        }
        res.header(LOCATION, url);
    }

    @Override
    public boolean isSync() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public boolean isUni() {
        return true;
    }
}
