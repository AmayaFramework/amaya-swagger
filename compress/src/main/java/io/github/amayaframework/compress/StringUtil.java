package io.github.amayaframework.compress;

final class StringUtil {
    private StringUtil() {
    }

    static String trim(int start, String string) {
        var end = string.length();
        while (start < end && string.charAt(start) <= ' ') {
            start++;
        }
        while (end > start && string.charAt(end - 1) <= ' ') {
            end--;
        }
        if (start >= end) {
            return null;
        }
        return string.substring(start, end);
    }
}
