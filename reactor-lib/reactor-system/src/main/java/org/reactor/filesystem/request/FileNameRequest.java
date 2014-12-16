package org.reactor.filesystem.request;

import org.reactor.annotation.ReactorRequestParameter;

public class FileNameRequest {

    @ReactorRequestParameter(shortName = "f", required = true)
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
