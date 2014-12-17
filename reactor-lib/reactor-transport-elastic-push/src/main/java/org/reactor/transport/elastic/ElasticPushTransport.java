package org.reactor.transport.elastic;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.quartz.SchedulerException;
import org.reactor.response.ReactorResponse;
import org.reactor.transport.ReactorRequestHandler;
import org.reactor.transport.TransportProperties;
import org.reactor.transport.alive.KeepAliveReactorMessageTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ElasticPushTransport extends KeepAliveReactorMessageTransport {

    private final static Logger LOGGER = LoggerFactory.getLogger(ElasticPushTransport.class);

    private ReactorRequestHandler requestHandler;
    private TransportClient client;
    private ElasticPushScheduler pushScheduler;

    @Override
    protected void stopTransportKeptAlive() {
        try {
            pushScheduler.stop();
            pushScheduler = null;
        } catch (SchedulerException e) {
            LOGGER.error("Unable to stop Elastic push transport", e);
        }
    }

    @Override
    protected void startTransportKeptAlive(TransportProperties transportProperties, ReactorRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        try {
            startElasticPushTransport(new ElasticTransportProperties(transportProperties));
        } catch (Exception e) {
            LOGGER.error("An error occurred while initializing Elastic push transport:", e);
        }
    }

    private void startElasticPushTransport(ElasticTransportProperties elasticTransportProperties) throws Exception {
        client = new TransportClient();
        client.addTransportAddress(new InetSocketTransportAddress(elasticTransportProperties.getHost(),
            elasticTransportProperties.getPort()));

        startElasticPushScheduler(elasticTransportProperties.getConfigurationFileStream());
    }

    private void startElasticPushScheduler(InputStream configurationFileStream) throws Exception {
        pushScheduler = new ElasticPushScheduler(requestHandler, client);
        pushScheduler.loadFromStream(configurationFileStream);
        pushScheduler.start();
    }

    @Override
    public void broadcast(ReactorResponse reactorResponse) {
        // do nothing
    }

    @Override
    public boolean isRunning() {
        return pushScheduler != null;
    }
}
