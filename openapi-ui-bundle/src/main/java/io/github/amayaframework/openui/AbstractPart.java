package io.github.amayaframework.openui;

import io.github.amayaframework.http.MimeType;

/**
 * TODO
 */
public abstract class AbstractPart implements Part {
    protected final String name;
    protected final MimeType type;

    /**
     * TODO
     * @param name
     * @param type
     */
    protected AbstractPart(String name, MimeType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public MimeType mimeType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractPart)) {
            return false;
        }
        return name.equals(((AbstractPart) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
