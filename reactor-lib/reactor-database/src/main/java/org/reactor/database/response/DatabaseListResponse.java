package org.reactor.database.response;

import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;

public class DatabaseListResponse implements ReactorResponse {

    private final Iterable<String> dbNames;

    public DatabaseListResponse(Iterable<String> dbNames) {
        this.dbNames = dbNames;
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) throws Exception {
        int index = 1;
        for (String dbName : dbNames) {
            responseRenderer.renderListLine(index, dbName, (long elementIndex, String listElement) -> elementIndex + ". " +listElement);
            index++;
        }
    }
}
