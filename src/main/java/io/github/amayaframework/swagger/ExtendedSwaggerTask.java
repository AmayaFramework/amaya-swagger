package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jsync.Futures;
import io.github.amayaframework.compress.EncodingNegotiator;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.openui.Part;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * TODO
 */
public final class ExtendedSwaggerTask extends AbstractSwaggerTask {
    private final Map<String, Part> parts;
    private final String root;
    private final String slashRoot;

    /**
     * TODO
     * @param parts
     * @param root
     * @param negotiator
     */
    public ExtendedSwaggerTask(Map<String, Part> parts, String root, EncodingNegotiator negotiator) {
        super(negotiator);
        this.parts = parts;
        this.root = root;
        this.slashRoot = root + '/';
    }

    @Override
    public void run(HttpContext context, Task<HttpContext> next) throws Throwable {
        var req = context.request();
        var path = req.path();
        // 1. Path matches root without trailing '/' => redirect to root + '/'
        if (path.equals(root)) {
            redirect(context.response(), slashRoot);
            return;
        }
        // 2. Try to look up a part by path; if found, send it
        var part = parts.get(path);
        if (part != null) {
            sendPart(req, context.response(), part);
            return;
        }
        // 3. Otherwise, decide whether to return 404 or pass to the next task
        // 4. If the path starts with the root => return 404
        if (path.startsWith(slashRoot)) {
            context.response().sendError(HttpCode.NOT_FOUND);
        } else {
            // 5. For all other cases, delegate to the next task since we cannot be 100% sure
            //    whether the requested path belongs to Swagger or not
            //    Example: /api/openapi.json => found, served
            //             /api/openapi1.json => not found, passed to next task
            next.run(context);
        }
    }

    @Override
    public CompletableFuture<Void> runAsync(HttpContext context, Task<HttpContext> next) {
        var req = context.request();
        var path = req.path();
        // 1. Path matches root without trailing '/' => redirect to root + '/'
        if (path.equals(root)) {
            redirect(context.response(), slashRoot);
            return CompletableFuture.completedFuture(null);
        }
        // 2. Try to look up a part by path; if found, send it
        var part = parts.get(path);
        if (part != null) {
            return sendPartAsync(req, context.response(), part);
        }
        // 3. Otherwise, decide whether to return 404 or pass to the next task
        // 4. If the path starts with the root => return 404
        if (path.startsWith(slashRoot)) {
            return Futures.run(() -> context.response().sendError(HttpCode.NOT_FOUND));
        } else {
            // 5. For all other cases, delegate to the next task since we cannot be 100% sure
            //    whether the requested path belongs to Swagger or not
            //    Example: /api/openapi.json => found, served
            //             /api/openapi1.json => not found, passed to next task
            return next.runAsync(context);
        }
    }
}
