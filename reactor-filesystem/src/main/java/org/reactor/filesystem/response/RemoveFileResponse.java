package org.reactor.filesystem.response;

import org.reactor.response.StringReactorResponse;

public class RemoveFileResponse extends StringReactorResponse {

    public RemoveFileResponse(String fileName, boolean removed) {
        super("File '%s' removed: %s", fileName, "" + removed);
    }
}
