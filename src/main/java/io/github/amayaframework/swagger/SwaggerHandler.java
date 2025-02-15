package io.github.amayaframework.swagger;

import com.github.romanqed.jfunc.Function0;
import com.github.romanqed.jfunc.Runnable1;
import com.github.romanqed.jfunc.Runnable2;
import io.github.amayaframework.context.HttpContext;
import io.github.amayaframework.context.HttpResponse;
import io.github.amayaframework.http.HttpCode;
import io.github.amayaframework.http.MimeData;
import io.github.amayaframework.openui.OpenUI;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

final class SwaggerHandler implements Runnable2<HttpContext, Runnable1<HttpContext>> {
    // Mime type solver and cached types
    final MimeTyper typer;
    final MimeData yamlData;
    final MimeData htmlData;
    // Path data: root prefix, swagger path, etc
    final String root;
    final int rootLength;
    final String swagger;
    // OpenUI data: open ui instance and open ui index page name
    final OpenUI ui;
    final String index;
    // Open api manifests
    final Map<String, Function0<InputStream>> manifests;

    SwaggerHandler(MimeTyper typer,
                   String root,
                   String swagger,
                   OpenUI ui,
                   Map<String, Function0<InputStream>> manifests) {
        // Typer
        this.typer = typer;
        this.yamlData = typer.get("yaml");
        this.htmlData = typer.get("html");
        // Paths
        this.root = root;
        this.rootLength = root.length();
        this.swagger = swagger;
        // Open UI
        this.ui = ui;
        this.index = ui.getIndex();
        // Manifests
        this.manifests = manifests;
    }

    private static String getExtension(String file) {
        var index = file.lastIndexOf('.');
        if (index < 0) {
            return null;
        }
        return file.substring(index + 1);
    }

    private void sendIndex(HttpResponse response) throws IOException {
        try (var stream = ui.getInputStream(index)) {
            response.setStatus(HttpCode.OK);
            response.setMimeData(htmlData);
            stream.transferTo(response.getOutputStream());
        }
    }

    private void sendYaml(HttpResponse response, Function0<InputStream> provider) throws Throwable {
        try (var stream = provider.invoke()) {
            response.setStatus(HttpCode.OK);
            response.setMimeData(yamlData);
            stream.transferTo(response.getOutputStream());
        }
    }

    private void sendFile(HttpResponse response, String file) throws IOException {
        try (var stream = ui.getInputStream(file)) {
            if (stream == null) {
                response.sendError(HttpCode.NOT_FOUND, "File " + file + " not found");
                return;
            }
            response.setStatus(HttpCode.OK);
            var extension = getExtension(file);
            if (extension != null) {
                var data = typer.get(extension);
                if (data != null) {
                    response.setMimeData(data);
                }
            }
            stream.transferTo(response.getOutputStream());
        }
    }

    @Override
    public void run(HttpContext context, Runnable1<HttpContext> next) throws Throwable {
        var request = context.getRequest();
        var path = PathUtil.normalize(request.getPath());
        // Check if path equals swagger ui uri
        if (swagger.equals(path) || root.equals(path)) {
            sendIndex(context.getResponse());
            return;
        }
        // Check if path starts with root
        if (!path.startsWith(root)) {
            next.run(context);
            return;
        }
        var tail = path.substring(rootLength + 1);
        if (tail.isEmpty()) {
            context.getResponse().sendError(HttpCode.NOT_FOUND, "Empty filename");
            return;
        }
        // Check if there are open api file with such path
        var manifest = manifests.get(tail);
        if (manifest != null) {
            sendYaml(context.getResponse(), manifest);
            return;
        }
        // Try to lookup for static part
        sendFile(context.getResponse(), tail);
    }
}
