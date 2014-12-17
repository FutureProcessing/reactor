package org.reactor.transport.elastic;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.reactor.transport.elastic.job.ElasticPushReactorResponseJob.*;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.elasticsearch.client.transport.TransportClient;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.reactor.transport.ReactorRequestHandler;
import org.reactor.transport.elastic.job.ElasticPushReactorResponseJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElasticPushScheduler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ElasticPushScheduler.class);

    private static final String TAG_DEFINITION = "push-definition";
    private static final String ATTRIBUTE_DOC_TYPE = "doc-type";
    private static final String ATTRIBUTE_INDEX = "index";
    private static final String ATTRIBUTE_INTERVAL = "interval";

    private final ReactorRequestHandler requestHandler;
    private final TransportClient elasticClient;
    private Scheduler scheduler;

    public ElasticPushScheduler(ReactorRequestHandler requestHandler, TransportClient elasticClient)
            throws SchedulerException {
        this.requestHandler = requestHandler;
        this.elasticClient = elasticClient;

        initializeQuartz();
    }

    public void addDefinition(String index, String docType, String interval, String reactorRequest)
            throws SchedulerException {
        LOGGER.debug("Adding new Quartz job definition, request = {}, interval = {}", reactorRequest, interval);

        JobDetail quartzJob = newJob(ElasticPushReactorResponseJob.class)
            .withIdentity(docType, index)
            .usingJobData(DATA_REACTOR_REQUEST, reactorRequest)
            .usingJobData(DATA_ELASTIC_INDEX, index)
            .usingJobData(DATA_ELASTIC_DOC_TYPE, docType).build();
        Trigger trigger = newTrigger().withSchedule(cronSchedule(interval)).build();
        scheduler.scheduleJob(quartzJob, trigger);
    }

    private void initializeQuartz() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();
        scheduler.getContext().put(REF_ELASTIC_CLIENT, elasticClient);
        scheduler.getContext().put(REF_REQUEST_HANDLER, requestHandler);
    }

    public void loadFromStream(InputStream configurationFileStream) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document definitionsXML = dBuilder.parse(configurationFileStream);

        NodeList definitions = definitionsXML.getElementsByTagName(TAG_DEFINITION);
        for (int index = 0; index < definitions.getLength(); index++) {
            Node definitionNode = definitions.item(index);
            addDefinitionFromXMLNode((Element) definitionNode);
        }
    }

    private void addDefinitionFromXMLNode(Element definitionNode) {
        String index = definitionNode.getAttribute(ATTRIBUTE_INDEX);
        String docType = definitionNode.getAttribute(ATTRIBUTE_DOC_TYPE);
        String interval = definitionNode.getAttribute(ATTRIBUTE_INTERVAL);
        String reactorInput = definitionNode.getTextContent();

        try {
            addDefinition(index, docType, interval, reactorInput);
        } catch (SchedulerException e) {
            LOGGER.error("Unable to add new quartz job", e);
        }
    }

    public void start() throws SchedulerException {
        scheduler.start();
    }

    public void stop() throws SchedulerException {
        scheduler.shutdown();
    }
}
