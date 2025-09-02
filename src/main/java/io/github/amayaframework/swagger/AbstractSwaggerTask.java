package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.TaskConsumer;
import com.github.romanqed.jfunc.Exceptions;
import com.github.romanqed.jsync.Futures;
import io.github.amayaframework.compress.CompressEncoder;
import io.github.amayaframework.compress.CompressNegotiator;
import io.github.amayaframework.compress.IdentityEncoder;
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

/**
 * Base implementation of a Swagger-related {@link TaskConsumer} that provides
 * utilities for serving {@link Part} resources over HTTP with optional compression.
 * <p>
 * This class integrates a {@link CompressNegotiator} for handling
 * {@code Accept-Encoding} negotiation and provides both synchronous and
 * asynchronous methods for sending parts to an {@link HttpResponse}.
 * It also offers helpers for constructing MIME metadata and handling redirects.
 * <p>
 * Subclasses implement application-specific logic, while this base
 * class centralizes common behaviors for serving Swagger UI resources.
 */
public abstract class AbstractSwaggerTask implements TaskConsumer<HttpContext> {
    protected static final String ACCEPT_ENCODING = "Accept-Encoding";
    protected static final String CONTENT_ENCODING = "Content-Encoding";
    protected static final String LOCATION = "Location";
    protected final CompressNegotiator negotiator;

    /**
     * Creates a new Swagger task with the given compression negotiator.
     *
     * @param negotiator the negotiator used to select a suitable
     *                   {@link CompressEncoder} based on client
     *                   {@code Accept-Encoding} headers
     */
    protected AbstractSwaggerTask(CompressNegotiator negotiator) {
        this.negotiator = negotiator;
    }

    /**
     * Builds a {@link MimeData} instance from the given {@link Part}.
     * <p>
     * If the part is textual and provides a charset, the charset is added
     * as a parameter to the MIME data.
     *
     * @param part the resource part
     * @return a {@link MimeData} instance for the part
     */
    protected static MimeData getMimeData(Part part) {
        var type = part.mimeType();
        var data = new MimeData(type);
        if (type.isText() && part.charset() != null) {
            data.setParam("charset", part.charset());
        }
        return data;
    }

    /**
     * Sends the given part synchronously to the client.
     *
     * @param res     the HTTP response
     * @param encoder the encoder to apply, or {@code null} for identity
     * @param part    the resource part to send
     * @throws IOException if an I/O error occurs
     */
    protected static void sendPart(HttpResponse res, CompressEncoder encoder, Part part) throws IOException {
        res.mimeData(getMimeData(part));
        OutputStream outputStream = res.outputStream();
        if (encoder != null) {
            res.header(CONTENT_ENCODING, encoder.name());
            outputStream = encoder.encode(outputStream);
        }
        try (var inputStream = part.inputStream()) {
            inputStream.transferTo(outputStream);
        } finally {
            outputStream.close();
        }
    }

    /**
     * Sends the given part asynchronously to the provided stream.
     * <p>
     * The stream is closed upon completion.
     *
     * @param stream the output stream to write to
     * @param part   the resource part to send
     * @return a {@link CompletableFuture} representing the asynchronous transfer
     */
    protected static CompletableFuture<Void> sendPartAsync(OutputStream stream, Part part) {
        return Futures.run(() -> {
            try (var inputStream = part.inputStream()) {
                inputStream.transferTo(stream);
            } finally {
                stream.close();
            }
        });
    }

    /**
     * Sends the given part asynchronously to the client.
     *
     * @param res     the HTTP response
     * @param encoder the encoder to apply, or {@code null} for identity
     * @param part    the resource part to send
     * @return a {@link CompletableFuture} representing the asynchronous transfer
     * @throws IOException if an I/O error occurs
     */
    protected static CompletableFuture<Void> sendPartAsync(HttpResponse res, CompressEncoder encoder, Part part) throws IOException {
        res.mimeData(getMimeData(part));
        OutputStream outputStream = res.outputStream();
        if (encoder != null) {
            res.header(CONTENT_ENCODING, encoder.name());
            outputStream = encoder.encode(outputStream);
        }
        return sendPartAsync(outputStream, part);
    }

    /**
     * Sets a permanent redirect response to the specified URL.
     * <p>
     * Uses {@link HttpCode#MOVED_PERMANENTLY} for HTTP/1.0 clients
     * and {@link HttpCode#PERMANENT_REDIRECT} for HTTP/1.1 or later.
     *
     * @param res the HTTP response
     * @param url the target URL
     */
    protected static void redirect(HttpResponse res, String url) {
        if (res.httpVersion().before(HttpVersion.HTTP_1_1)) {
            res.status(HttpCode.MOVED_PERMANENTLY);
        } else {
            res.status(HttpCode.PERMANENT_REDIRECT);
        }
        res.header(LOCATION, url);
    }

    /**
     * Sends the given part synchronously, negotiating compression
     * using the request's {@code Accept-Encoding} header.
     * <p>
     * If no acceptable encoding is found, responds with
     * {@link HttpCode#NOT_ACCEPTABLE}.
     *
     * @param req  the HTTP request
     * @param res  the HTTP response
     * @param part the resource part to send
     * @throws IOException if an I/O error occurs
     */
    protected void sendPart(HttpRequest req, HttpResponse res, Part part) throws IOException {
        var encoder = negotiator.negotiate(req.header(ACCEPT_ENCODING));
        if (encoder == null) {
            res.status(HttpCode.NOT_ACCEPTABLE);
            res.contentLength(0);
            return;
        }
        sendPart(res, encoder == IdentityEncoder.INSTANCE ? null : encoder, part);
    }

    /**
     * Sends the given part asynchronously, negotiating compression
     * using the request's {@code Accept-Encoding} header.
     * <p>
     * If no acceptable encoding is found, responds with
     * {@link HttpCode#NOT_ACCEPTABLE} and returns a completed future.
     *
     * @param req  the HTTP request
     * @param res  the HTTP response
     * @param part the resource part to send
     * @return a {@link CompletableFuture} representing the asynchronous transfer
     */
    protected CompletableFuture<Void> sendPartAsync(HttpRequest req, HttpResponse res, Part part) {
        var encoder = negotiator.negotiate(req.header(ACCEPT_ENCODING));
        if (encoder == null) {
            res.status(HttpCode.NOT_ACCEPTABLE);
            res.contentLength(0);
            return CompletableFuture.completedFuture(null);
        }
        try {
            return sendPartAsync(res, encoder == IdentityEncoder.INSTANCE ? null : encoder, part);
        } catch (IOException e) {
            Exceptions.throwAny(e);
            return null;
        }
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
