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
 * Default {@link AbstractSwaggerTask} implementation for serving Swagger UI resources.
 * <p>
 * This task handles requests under a configured root path, serving static
 * {@link Part} resources such as HTML, CSS, and JavaScript that make up
 * the Swagger UI frontend.
 * <p>
 * Behavior:
 * <ul>
 *   <li>If the request path is shorter than or does not start with the configured root,
 *       control is delegated to the {@code next} task.</li>
 *   <li>If the request path exactly matches the root, the client is redirected to
 *       {@code root + '/'}.</li>
 *   <li>If the request path is {@code root + '/'}, the configured index {@link Part}
 *       is served.</li>
 *   <li>If the request path corresponds to a known part, that part is served.</li>
 *   <li>Otherwise, the response is {@link HttpCode#NOT_FOUND}.</li>
 * </ul>
 * Both synchronous ({@link #run(HttpContext, Task)}) and asynchronous
 * ({@link #runAsync(HttpContext, Task)}) execution are supported.
 */
public final class StandardSwaggerTask extends AbstractSwaggerTask {
    private final Map<String, Part> parts;
    private final Part index;
    private final String root;
    private final String slashRoot;
    private final int rootLength;

    /**
     * Creates a new Swagger UI serving task.
     *
     * @param parts      a map of resource names to parts (e.g. CSS, JS, icons)
     * @param index      the index page part (usually {@code index.html})
     * @param root       the root path under which Swagger UI is mounted
     * @param negotiator the compression negotiator to use for responses
     */
    public StandardSwaggerTask(Map<String, Part> parts, Part index, String root, CompressNegotiator negotiator) {
        super(negotiator);
        this.parts = parts;
        this.index = index;
        this.root = root;
        this.slashRoot = root + '/';
        this.rootLength = root.length();
    }

    @Override
    public void run(HttpContext context, Task<HttpContext> next) throws Throwable {
        var req = context.request();
        var path = req.path();
        var length = path.length();
        // 1. Path shorter than root => definitely NOT ROOT
        // 2. Path does not start with root => definitely NOT ROOT
        if (length < rootLength || !path.startsWith(root)) {
            next.run(context);
            return;
        }
        // 3. Lengths are equal => this is the root itself (with redirect to + /)
        if (length == rootLength) {
            redirect(context.response(), slashRoot);
            return;
        }
        // 4. Character after root is NOT '/' => definitely NOT ROOT
        if (path.charAt(rootLength) != '/') {
            next.run(context);
            return;
        }
        // 5. Nothing after '/' => definitely ROOT itself (no redirect)
        if (rootLength == length - 1) {
            sendPart(req, context.response(), index);
            return;
        }
        // 6. Starts with root and has a tail
        var res = context.response();
        var tail = path.substring(rootLength + 1);
        var part = parts.get(tail);
        if (part == null) {
            res.sendError(HttpCode.NOT_FOUND);
        } else {
            sendPart(req, res, part);
        }
    }

    @Override
    public CompletableFuture<Void> runAsync(HttpContext context, Task<HttpContext> next) {
        var req = context.request();
        var path = req.path();
        var length = path.length();
        // 1. Path shorter than root => definitely NOT ROOT
        // 2. Path does not start with root => definitely NOT ROOT
        if (length < rootLength || !path.startsWith(root)) {
            return next.runAsync(context);
        }
        // 3. Lengths are equal => this is the root itself (with redirect to + /)
        if (length == rootLength) {
            redirect(context.response(), slashRoot);
            return CompletableFuture.completedFuture(null);
        }
        // 4. Character after root is NOT '/' => definitely NOT ROOT
        if (path.charAt(rootLength) != '/') {
            return next.runAsync(context);
        }
        // 5. Nothing after '/' => definitely ROOT itself (no redirect)
        if (rootLength == length - 1) {
            return sendPartAsync(req, context.response(), index);
        }
        // 6. Starts with root and has a tail
        var res = context.response();
        var tail = path.substring(rootLength + 1);
        var part = parts.get(tail);
        if (part == null) {
            try {
                res.sendError(HttpCode.NOT_FOUND);
            } catch (IOException e) {
                return CompletableFuture.failedFuture(e);
            }
            return CompletableFuture.completedFuture(null);
        }
        return sendPartAsync(req, res, part);
    }
}
