package org.reactor.filesystem.process;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RunProcessReactorLinuxIntegrationTest extends AbstractUnitTest {

    private RunProcessReactor reactor;

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
