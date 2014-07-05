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
}
