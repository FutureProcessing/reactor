package org.reactor.response;

import static com.google.common.base.Throwables.getRootCause;
import static java.lang.String.format;
import java.io.PrintWriter;
import java.io.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionReactorResponse implements ReactorResponse {

    private final static Logger LOG = LoggerFactory.getLogger(ExceptionReactorResponse.class);
    
    private final Throwable throwable;

    public ExceptionReactorResponse(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public void renderResponse(Writer responseWriter) throws Exception {
        LOG.error("An error occurred", throwable);
        new PrintWriter(responseWriter, true).println(getExceptionMessage());
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private String getExceptionMessage() {
        return format("Error:: %s", getRootCause(throwable).getMessage());
    }

}
