package org.reactor.utils;

import static com.google.common.base.CharMatcher.anyOf;
import static java.lang.String.format;
import java.util.Iterator;

public class StringUtils {

    public static final char ESCAPE_QUOTE = '"';

    private static class EscapingStringsIterator implements Iterator<String> {

        private static final String WHITESPACE = " ";
        private final Iterator<String> strings;
        private final char escapeCharacter;

        public EscapingStringsIterator(Iterable<String> strings, char escapeCharacter) {
            this.strings = strings.iterator();
            this.escapeCharacter = escapeCharacter;
        }

        @Override
        public boolean hasNext() {
            return strings.hasNext();
        }

        @Override
        public String next() {
            String nextString = strings.next();
            if (anyOf(WHITESPACE).matchesAnyOf(nextString)) {
                return format("%s%s%s", escapeCharacter, nextString, escapeCharacter);
            }
            return nextString;
        }

        @Override
        public void remove() {
            // do nothing
        }
    }

    public static Iterable<String> quotedIterable(final Iterable<String> strings) {
        return () -> new EscapingStringsIterator(strings, ESCAPE_QUOTE);
    }

    public static boolean matchesWildcard(String input, String wildcard) {
        String text = input + '\0';
        String pattern = wildcard + '\0';

        int N = pattern.length();

        boolean[] states = new boolean[N + 1];
        boolean[] old = new boolean[N + 1];
        old[0] = true;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            states = new boolean[N + 1];
            for (int j = 0; j < N; j++) {
                char p = pattern.charAt(j);
                if (old[j] && (p == '*')) old[j + 1] = true;
                if (old[j] && (p == c)) states[j + 1] = true;
                if (old[j] && (p == '.')) states[j + 1] = true;
                if (old[j] && (p == '*')) states[j] = true;
                if (old[j] && (p == '*')) states[j + 1] = true;
            }
            old = states;
        }
        return states[N];
    }
}
