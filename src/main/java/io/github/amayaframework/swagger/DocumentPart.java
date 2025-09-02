package io.github.amayaframework.swagger;

import io.github.amayaframework.http.MimeType;
import io.github.amayaframework.openui.AbstractPart;

import java.io.IOException;
import java.io.InputStream;

final class DocumentPart extends AbstractPart {
    private final String charset;
    private final InputStreamProvider provider;

    DocumentPart(String name, MimeType type, String charset, InputStreamProvider provider) {
        super(name, type);
        this.charset = charset;
        this.provider = provider;
    }

    static DocumentPart of(OpenApiSource source) {
        return new DocumentPart(
                PathUtil.normalize(source.uri()),
                source.format().type,
                source.charset(),
                source.provider()
        );
    }

    @Override
    public String charset() {
        return charset;
    }

    @Override
    public InputStream inputStream() throws IOException {
        return provider.get();
    }
}
