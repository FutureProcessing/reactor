package org.reactor.system.process;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.lang.System.getProperty;
import static org.junit.Assume.assumeTrue;

public class RunProcessReactorLinuxIntegrationTest extends AbstractUnitTest {

    private RunProcessReactor reactor;

    @Before
    public void runIfLinux() {
        assumeTrue(!getProperty("os.name").contains("Windows"));
    }


    @Test
    public void shouldGetSomeTextAfterRunningSystemProcess() throws IOException, InterruptedException, TimeoutException {
        // given
        givenRunProcessReactor();

        // when
        String processResult = reactor.runProcess("uname", "-a");

        // then
        Assertions.assertThat(processResult).contains("Linux");

    }

    private void givenRunProcessReactor() {
        reactor = new RunProcessReactor();
    }
}
