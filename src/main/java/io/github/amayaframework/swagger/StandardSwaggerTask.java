package io.github.amayaframework.swagger;

import com.github.romanqed.jconv.Task;
import com.github.romanqed.jconv.TaskConsumer;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.openui.Part;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class StandardSwaggerTask implements TaskConsumer<HttpContext> {
    private final Map<String, Part> parts;
    private final Part index;
    private final String root;
    private final int rootLength;

    public StandardSwaggerTask(Map<String, Part> parts, Part index, String root) {
        this.parts = parts;
        this.index = index;
        this.root = root;
        this.rootLength = root.length();
    }

    @Override
    public void run(HttpContext context, Task<HttpContext> task) throws Throwable {
        var rq = context.request();
        var rp = context.response();
        var path = rq.getPath();
        var length = path.length();
        // 1. Путь короче корня - гарантированно НЕ КОРЕНЬ
        // 2. Путь не начинается с корня - гарантированно НЕ КОРЕНЬ
        if (length < rootLength || !path.startsWith(root)) {
            // notRoot(rp);
            //or next.run(context) in release
            return;
        }
        // 3. Длины равны - это сам корень (с redirect в + /)
        if (length == rootLength) {
            //exactRoot(rp, true);
            return;
        }
        // 4. После корня идет НЕ / - это гарантированно НЕ КОРЕНЬ
        if (path.charAt(rootLength) != '/') {
            //notRoot(rp);
            //or next.run(context) in release
            return;
        }
        // 5. После / ничего нет - это гарантированно САМ КОРЕНЬ (без redirect)
        if (rootLength == length - 1) {
            //exactRoot(rp, false);
            return;
        }
        // 6. Начинается с корня и есть хвост
        var tail = path.substring(rootLength + 1);
        rp.getWriter().println("Root found, tail = " + tail);
        var part = parts.get(tail);
        if (part == null) {
            // not found
        } else {
            try (var is = part.inputStream()) {
                is.transferTo(rp.getOutputStream());
            }
        }
    }

    @Override
    public CompletableFuture<Void> runAsync(HttpContext context, Task<HttpContext> task) {
        return null;
    }
}
