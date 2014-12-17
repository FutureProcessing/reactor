package org.reactor.transport.elastic.job;

import org.elasticsearch.client.transport.TransportClient;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.reactor.transport.ReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticPushReactorResponseJob implements Job {

    public static final String REF_ELASTIC_CLIENT = "_elastic";
    public static final String REF_REQUEST_HANDLER = "_handler";
    public static final String DATA_REACTOR_REQUEST = "reactorRequest";
    public static final String DATA_ELASTIC_INDEX = "index";
    public static final String DATA_ELASTIC_DOC_TYPE = "docType";
    private final static Logger LOGGER = LoggerFactory.getLogger(ElasticPushReactorResponseJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            String reactorRequest = dataMap.getString(DATA_REACTOR_REQUEST);
            String index = dataMap.getString(DATA_ELASTIC_INDEX);
            String docType = dataMap.getString(DATA_ELASTIC_DOC_TYPE);

            ElasticPushReactorResponseJobExecutor executor = new ElasticPushReactorResponseJobExecutor(
                getElasticClient(jobExecutionContext), getReactorRequestHandler(jobExecutionContext));
            executor.run(reactorRequest, index, docType);
        } catch (SchedulerException e) {
            LOGGER.error("An error occurred while executing job", e);
            throw new JobExecutionException(e);
        }


    }

    private TransportClient getElasticClient(JobExecutionContext jobExecutionContext) throws SchedulerException {
        SchedulerContext context = jobExecutionContext.getScheduler().getContext();
        return (TransportClient) context.get(REF_ELASTIC_CLIENT);
    }

    private ReactorRequestHandler getReactorRequestHandler(JobExecutionContext jobExecutionContext) throws SchedulerException {
        SchedulerContext context = jobExecutionContext.getScheduler().getContext();
        return (ReactorRequestHandler) context.get(REF_REQUEST_HANDLER);
    }
}
