package org.reactor.utils;

import static java.lang.String.format;
import java.util.Arrays;
import java.util.Iterator;

public class StringUtils {

    private static class EscapingStringsIterator implements Iterator<String> {

        private final Iterator<String> strings;
        private final char escapeCharacter;
        private int position;

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
            return format("%s%s%s", escapeCharacter, strings.next(), escapeCharacter);
        }

        @Override
        public void remove() {
            // do nothing
        }
    }

    public static Iterable<String> quotedIterable(String[] strings) {
        return quotedIterable(Arrays.asList(strings));
    }

    public static Iterable<String> quotedIterable(final Iterable<String> strings) {
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new EscapingStringsIterator(strings, '"');
            }
        };
    }
}
