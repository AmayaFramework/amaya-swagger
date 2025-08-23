package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.Task;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.openui.Part;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class StandardSwaggerTask extends AbstractSwaggerTask {
    private final Map<String, Part> parts;
    private final Part index;
    private final String root;
    private final String slashRoot;
    private final int rootLength;

    public StandardSwaggerTask(Map<String, Part> parts, Part index, String root, EncodingNegotiator negotiator) {
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
        // 1. Путь короче корня - гарантированно НЕ КОРЕНЬ
        // 2. Путь не начинается с корня - гарантированно НЕ КОРЕНЬ
        if (length < rootLength || !path.startsWith(root)) {
            next.run(context);
            return;
        }
        // 3. Длины равны - это сам корень (с redirect в + /)
        if (length == rootLength) {
            redirect(context.response(), slashRoot);
            return;
        }
        // 4. После корня идет НЕ / - это гарантированно НЕ КОРЕНЬ
        if (path.charAt(rootLength) != '/') {
            next.run(context);
            return;
        }
        // 5. После / ничего нет - это гарантированно САМ КОРЕНЬ (без redirect)
        if (rootLength == length - 1) {
            sendPart(req, context.response(), index);
            return;
        }
        // 6. Начинается с корня и есть хвост
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
    public CompletableFuture<Void> runAsync(HttpContext context, Task<HttpContext> task) {
        // TODO Implement async
        return null;
    }
}
