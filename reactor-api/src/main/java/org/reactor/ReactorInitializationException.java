package org.reactor;

import static java.lang.String.format;

public class ReactorInitializationException extends RuntimeException {

    public ReactorInitializationException(String message) {
        super(message);
    }

    public ReactorInitializationException(String format, Object... params) {
        super(format(format, params));
    }

    public ReactorInitializationException(Throwable exception) {
        super(exception);
    }
}
