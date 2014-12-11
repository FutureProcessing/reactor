package org.reactor.filesystem.exception;

import static java.lang.String.format;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String fileName) {
        super(format("Unable to find file with name: %s", fileName));
    }
}
