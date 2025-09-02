package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.Task;
import io.github.amayaframework.compress.CompressNegotiator;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.openui.Part;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Flexible {@link AbstractSwaggerTask} implementation for serving Swagger UI resources.
 * <p>
 * Unlike {@link StandardSwaggerTask}, which is tied to a root prefix and a fixed index part,
 * this task looks up resources directly by their full path in a provided map.
 * <p>
 * Behavior:
 * <ul>
 *   <li>If the request path exactly matches the configured root, the client is redirected
 *       to {@code root + '/'}.</li>
 *   <li>If the request path matches a registered {@link Part}, that part is served.</li>
 *   <li>If the request path starts with {@code root + '/'}, but no part exists,
 *       the response is {@link HttpCode#NOT_FOUND}.</li>
 *   <li>For all other paths, control is delegated to the {@code next} task, since it is
 *       unclear whether the request belongs to Swagger or the rest of the application.</li>
 * </ul>
 * Both synchronous ({@link #run(HttpContext, Task)}) and asynchronous
 * ({@link #runAsync(HttpContext, Task)}) execution are supported.
 */
public final class ExtendedSwaggerTask extends AbstractSwaggerTask {
    private final Map<String, Part> parts;
    private final String root;
    private final String slashRoot;

    /**
     * Creates a new extended Swagger task.
     *
     * @param parts      a map of full request paths to parts
     *                   (e.g. {@code "/api/swagger-ui.css"} → CSS resource)
     * @param root       the root path under which Swagger UI is mounted
     * @param negotiator the compression negotiator to use for responses
     */
    public ExtendedSwaggerTask(Map<String, Part> parts, String root, CompressNegotiator negotiator) {
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
            try {
                context.response().sendError(HttpCode.NOT_FOUND);
                return CompletableFuture.completedFuture(null);
            } catch (IOException e) {
                return CompletableFuture.failedFuture(e);
            }
        }
        // 5. For all other cases, delegate to the next task since we cannot be 100% sure
        //    whether the requested path belongs to Swagger or not
        //    Example: /api/openapi.json => found, served
        //             /api/openapi1.json => not found, passed to next task
        return next.runAsync(context);
    }
}
