package org.reactor.system.request;

import org.reactor.annotation.ReactorRequestParameter;

public class FileNameMatchRequest {

    @ReactorRequestParameter(shortName = "f")
    private String fileNameMask;

    public FileNameMatchRequest() {
    }

    public FileNameMatchRequest(String fileNameMask) {
        this.fileNameMask = fileNameMask;
    }

    public String getFileNameMask() {
        return fileNameMask;
    }

    public void setFileNameMask(String fileNameMask) {
        this.fileNameMask = fileNameMask;
    }
}
