//package io.github.amayaframework.swagger;
//
//import com.github.romanqed.jfunc.Function0;
//import com.github.romanqed.jfunc.Runnable1;
//import com.github.romanqed.jfunc.Runnable2;
//import io.github.amayaframework.context.HttpContext;
//import io.github.amayaframework.context.HttpResponse;
//import io.github.amayaframework.http.HttpCode;
//import io.github.amayaframework.http.MimeData;
//import io.github.amayaframework.openui.OpenUI;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//
//final class SwaggerHandler implements Runnable2<HttpContext, Runnable1<HttpContext>> {
//    // Mime type solver and cached types
//    final MimeTyper typer;
//    final MimeData htmlData;
//    // Path data: root prefix, swagger path, etc
//    final String root;
//    final int rootLength;
//    final String swagger;
//    // OpenUI data: open ui instance and open ui index page name
//    final OpenUI ui;
//    final String index;
//    // Open api document providers
//    final Map<String, Function0<InputStream>> providers;
//
//    SwaggerHandler(MimeTyper typer,
//                   String root,
//                   String swagger,
//                   OpenUI ui,
//                   Map<String, Function0<InputStream>> providers) {
//        // Typer
//        this.typer = typer;
//        this.htmlData = typer.get("html");
//        // Paths
//        this.root = root;
//        this.rootLength = root.length();
//        this.swagger = swagger;
//        // Open UI
//        this.ui = ui;
//        this.index = ui.getIndex();
//        // Document providers
//        this.providers = providers;
//    }
//
//    private static String getExtension(String file) {
//        var index = file.lastIndexOf('.');
//        if (index < 0) {
//            return null;
//        }
//        return file.substring(index + 1);
//    }
//
//    private void sendIndex(HttpResponse response) throws IOException {
//        try (var stream = ui.getInputStream(index)) {
//            response.setStatus(HttpCode.OK);
//            response.setMimeData(htmlData);
//            stream.transferTo(response.getOutputStream());
//        }
//    }
//
//    private void sendDocument(HttpResponse response, String extension, Function0<InputStream> provider)
//            throws Throwable {
//        try (var stream = provider.invoke()) {
//            response.setStatus(HttpCode.OK);
//            var data = typer.get(extension);
//            if (data != null) {
//                response.setMimeData(data);
//            }
//            stream.transferTo(response.getOutputStream());
//        }
//    }
//
//    private void sendStaticFile(HttpResponse response, String file) throws IOException {
//        try (var stream = ui.getInputStream(file)) {
//            if (stream == null) {
//                response.sendError(HttpCode.NOT_FOUND, "File " + file + " not found");
//                return;
//            }
//            response.setStatus(HttpCode.OK);
//            var extension = getExtension(file);
//            if (extension != null) {
//                var data = typer.get(extension);
//                if (data != null) {
//                    response.setMimeData(data);
//                }
//            }
//            stream.transferTo(response.getOutputStream());
//        }
//    }
//
//    @Override
//    public void run(HttpContext context, Runnable1<HttpContext> next) throws Throwable {
//        var request = context.getRequest();
//        var path = PathUtil.normalize(request.getPath());
//        // Check if path equals swagger ui uri
//        if (swagger.equals(path) || root.equals(path)) {
//            sendIndex(context.getResponse());
//            return;
//        }
//        // Check if path starts with root
//        if (!path.startsWith(root)) {
//            next.run(context);
//            return;
//        }
//        var tail = path.substring(rootLength + 1);
//        // Check if there are open api file with such path
//        var provider = providers.get(tail);
//        if (provider != null) {
//            var extension = getExtension(tail);
//            sendDocument(context.getResponse(), extension, provider);
//            return;
//        }
//        // Try to lookup for static part
//        sendStaticFile(context.getResponse(), tail);
//    }
//}
