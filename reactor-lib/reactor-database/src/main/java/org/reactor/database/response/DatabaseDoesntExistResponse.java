package org.reactor.database.response;

import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;

public class DatabaseDoesntExistResponse implements ReactorResponse {

    private final String databaseId;

    public DatabaseDoesntExistResponse(String databaseId) {
        this.databaseId = databaseId;
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) throws Exception {
        responseRenderer.renderTextLine("There is no database with id: %s", databaseId);
    }
}
