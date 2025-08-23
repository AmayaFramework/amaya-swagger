package io.github.amayaframework.swagger;

import io.github.amayaframework.tokenize.Tokenizer;
import io.github.amayaframework.tokenize.Tokenizers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class PriorityEncodingHeaderParser implements EncodingHeaderParser {
    private final Tokenizer tokenizer;
    private final Map<String, Float> priority;

    public PriorityEncodingHeaderParser(Tokenizer tokenizer, Map<String, Float> priority) {
        this.tokenizer = tokenizer;
        this.priority = priority;
    }

    public PriorityEncodingHeaderParser(Map<String, Float> priority) {
        this(Tokenizers.PLAIN_TOKENIZER, priority);
    }

    private static Float parseParam(String param) {
        var index = param.indexOf('=');
        if (index < 0) {
            return null;
        }
        var flag = false;
        for (var i = 0; i < index; ++i) {
            var ch = param.charAt(i);
            if (Character.isWhitespace(ch)) {
                continue;
            }
            flag = ch == 'q';
        }
        if (!flag) {
            return null;
        }
        var rawQ = StringUtil.strip(index + 1, param);
        if (rawQ == null) {
            return null;
        }
        float q;
        try {
            q = Float.parseFloat(rawQ);
        } catch (NumberFormatException e) {
            return null;
        }
        if (q < 0) {
            return 0f;
        }
        if (q > 1) {
            return 1f;
        }
        return q;
    }

    private static void parse(String raw, Map<String, Float> map) {
        var split = Tokenizers.split(raw, ";").iterator();
        var encoding = StringUtil.strip(0, split.next());
        if (encoding == null) {
            return;
        }
        if (!split.hasNext()) {
            map.putIfAbsent(encoding, 1f); // do not override lower values
            return;
        }
        while (split.hasNext()) {
            var val = parseParam(split.next());
            if (val == null) {
                continue;
            }
            map.merge(encoding, val, Float::min);
            return;
        }
        map.putIfAbsent(encoding, 1f);
    }

    @Override
    public Iterable<String> parse(String header) {
        var iterable = tokenizer.tokenize(header, ",");
        var map = new HashMap<String, Float>();
        for (var value : iterable) {
            parse(value, map);
        }
        map.putIfAbsent("identity", 1f);
        var ret = new ArrayList<String>(map.size());
        map.forEach((k, v) -> {
            if (v > 0) {
                ret.add(k);
            }
        });
        ret.sort((l, r) -> {
            var cmp = map.get(r).compareTo(map.get(l));
            if (cmp == 0) {
                return priority.getOrDefault(r, 1f).compareTo(priority.getOrDefault(l, 1f));
            }
            return cmp;
        });
        return ret;
    }
}
