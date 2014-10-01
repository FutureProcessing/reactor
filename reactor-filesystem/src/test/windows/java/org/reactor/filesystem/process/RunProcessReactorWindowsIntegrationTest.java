package org.reactor.filesystem.process;

import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.fest.assertions.Assertions.assertThat;

public class RunProcessReactorWindowsIntegrationTest extends AbstractUnitTest {

    private RunProcessReactor reactor;

    @Test
    public void shouldGetSomeTextAfterRunningSystemProcess() throws IOException, InterruptedException, TimeoutException {
        // given
        givenRunProcessReactor();

        // when
        String processResult = reactor.runProcess("cmd", "/E", "ver");

        // then
        assertThat(processResult).contains("Windows");

    }

    private void givenRunProcessReactor() {
        reactor = new RunProcessReactor();
    }
}
