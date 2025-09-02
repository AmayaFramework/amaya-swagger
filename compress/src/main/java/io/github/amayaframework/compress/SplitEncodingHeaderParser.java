package io.github.amayaframework.compress;

import io.github.amayaframework.tokenize.Tokenizer;
import io.github.amayaframework.tokenize.Tokenizers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link EncodingHeaderParser} based on string splitting.
 * <p>
 * Parses the {@code Accept-Encoding} HTTP header into a prioritized list of encodings
 * with support for quality factors (<em>q-values</em>).
 * <ul>
 *   <li>Encodings without a {@code q} parameter default to priority {@code 1.0}.</li>
 *   <li>Invalid or missing q-values are ignored.</li>
 *   <li>Values are clamped to the range {@code [0.0, 1.0]}.</li>
 *   <li>The {@code identity} encoding is always included with a very low default
 *       priority ({@code 0.001}), ensuring it acts only as a fallback if no other
 *       encodings are acceptable.</li>
 * </ul>
 * The resulting list is sorted in descending order of priority, with ties resolved
 * by the optional server-side priorities map passed to {@link #parse(String, Map)}.
 * Client order is not preserved when q-values are equal.
 */
public final class SplitEncodingHeaderParser implements EncodingHeaderParser {
    private final Tokenizer tokenizer;

    /**
     * Creates a parser using the given {@link Tokenizer}
     * for splitting header values.
     *
     * @param tokenizer tokenizer instance used for splitting
     */
    public SplitEncodingHeaderParser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Creates a parser using {@link Tokenizers#PLAIN_TOKENIZER}.
     */
    public SplitEncodingHeaderParser() {
        this(Tokenizers.PLAIN_TOKENIZER);
    }

    private static Float parseParam(String param) {
        var index = param.indexOf('=');
        if (index < 0) {
            return null;
        }
        var flag = false;
        for (var i = 0; i < index; ++i) {
            var ch = param.charAt(i);
            if (ch <= ' ') {
                continue;
            }
            flag = ch == 'q';
        }
        if (!flag) {
            return null;
        }
        var rawQ = StringUtil.trim(index + 1, param);
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

    private static Comparator<? super String> selectComparator(Map<String, Float> cp, Map<String, Float> sp) {
        if (sp == null || sp.isEmpty()) {
            return new ClientComparator(cp);
        }
        return new ServerComparator(cp, sp);
    }

    private void parseHeader(String raw, Map<String, Float> map) {
        var split = tokenizer.tokenize(raw, ";").iterator();
        var encoding = StringUtil.trim(0, split.next());
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
    public Iterable<String> parse(String header, Map<String, Float> priorities) {
        var iterable = tokenizer.tokenize(header, ",");
        var map = new HashMap<String, Float>();
        for (var value : iterable) {
            parseHeader(value, map);
        }
        // Ensure identity encoding is always available unless disabled
        map.putIfAbsent("identity", 0.001f);
        var ret = new ArrayList<String>(map.size());
        map.forEach((k, v) -> {
            if (v > 0) {
                ret.add(k);
            }
        });
        ret.sort(selectComparator(map, priorities));
        return ret;
    }

    private static final class ClientComparator implements Comparator<String> {
        private final Map<String, Float> client;

        ClientComparator(Map<String, Float> client) {
            this.client = client;
        }

        @Override
        public int compare(String left, String right) {
            return client.get(right).compareTo(client.get(left));
        }
    }

    private static final class ServerComparator implements Comparator<String> {
        private final Map<String, Float> client;
        private final Map<String, Float> server;

        ServerComparator(Map<String, Float> client, Map<String, Float> server) {
            this.client = client;
            this.server = server;
        }

        @Override
        public int compare(String left, String right) {
            var cmp = client.get(right).compareTo(client.get(left));
            if (cmp == 0) {
                return server.getOrDefault(right, 1f).compareTo(server.getOrDefault(left, 1f));
            }
            return cmp;
        }
    }
}
