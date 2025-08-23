package io.github.amayaframework.swagger;

final class StringUtil {
    private StringUtil() {
    }

    static String strip(int start, String string) {
        var end = string.length();
        while (start < end && Character.isWhitespace(string.charAt(start))) {
            start++;
        }
        while (end > start && Character.isWhitespace(string.charAt(end - 1))) {
            end--;
        }
        if (start >= end) {
            return null;
        }
        return string.substring(start, end);
    }
}
