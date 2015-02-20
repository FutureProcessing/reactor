package org.reactor.system.response;

import org.reactor.response.ReactorResponse;

public class RemoveFileResponse implements ReactorResponse {

    private final String fileName;
    private final boolean removed;

    public RemoveFileResponse(String fileName, boolean removed) {
        this.fileName = fileName;
        this.removed = removed;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isRemoved() {
        return removed;
    }

    @Override
    public String toConsoleOutput() {
        return String.format("File '%s' removed: %s", fileName, removed);
    }
}
