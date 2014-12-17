package org.reactor.transport.elastic.job;

import java.io.StringWriter;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.json.JSONObject;
import org.reactor.renderer.JSONReactorResponseRenderer;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.transport.ReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticPushReactorResponseJobExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticPushReactorResponseJobExecutor.class);

    private static final String SENDER = "ELASTIC_PUSH";

    private final TransportClient elasticClient;
    private final ReactorRequestHandler reactorRequestHandler;

    public ElasticPushReactorResponseJobExecutor(TransportClient elasticClient,
                                                 ReactorRequestHandler reactorRequestHandler) {
        this.elasticClient = elasticClient;
        this.reactorRequestHandler = reactorRequestHandler;
    }

    public void run(String reactorRequest, String index, String docType) {
        ReactorResponseRenderer responseRenderer = new JSONReactorResponseRenderer();
        reactorRequestHandler.handleReactorRequest(new ReactorRequestInput(reactorRequest), SENDER, responseRenderer);
        StringWriter jsonContent = new StringWriter();
        responseRenderer.commit(jsonContent);

        JSONObject jsonObject = new JSONObject(jsonContent.toString());
        addTimestampProperty(jsonObject);
        LOGGER.debug("Pushing out reactor response: {}, index = {}, docType = {}", jsonObject.toString(), index,
            docType);
        IndexResponse indexResponse = elasticClient.prepareIndex(index, docType).setSource(jsonObject.toString())
            .execute().actionGet();
        LOGGER.debug("New entry created in Elastic: {}", indexResponse.getId());

    }

    private void addTimestampProperty(JSONObject jsonObject) {
        jsonObject.put("@timestamp", new Date().getTime());
    }
}
