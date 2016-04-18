package org.reactor.jenkins;

import static java.lang.String.format;

/**
 * @author grabslu
 */
public class JobNotFoundException extends RuntimeException {

  private static final String TEMPLATE_JOB_MISSING = "Unable to find job with name: %s.";

  public JobNotFoundException(String jobName) {
    super(format(TEMPLATE_JOB_MISSING, jobName));
  }
}
