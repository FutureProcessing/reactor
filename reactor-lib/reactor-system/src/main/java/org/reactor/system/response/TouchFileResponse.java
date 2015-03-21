package org.reactor.system.response;

import org.reactor.response.ReactorResponse;

public class TouchFileResponse implements ReactorResponse {

    private final String fileName;
    private final boolean touched;

    public TouchFileResponse(String fileName, boolean touched) {
        this.fileName = fileName;
        this.touched = touched;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isTouched() {
        return touched;
    }

    @Override
    public String toConsoleOutput() {
        return String.format("File '%s' touched: %s", fileName, touched);
    }
}
