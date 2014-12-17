package org.reactor.database.response;

import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;

public class DatabaseSizeResponse implements ReactorResponse {

    private final long databaseSize;

    public DatabaseSizeResponse(long databaseSize) {
        this.databaseSize = databaseSize;
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) throws Exception {
        responseRenderer.renderLongLine(databaseSize);
    }
}
