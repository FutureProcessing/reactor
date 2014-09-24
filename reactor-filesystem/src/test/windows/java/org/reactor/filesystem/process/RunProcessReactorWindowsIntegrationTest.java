package org.reactor.filesystem.process;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.io.IOException;

public class RunProcessReactorWindowsIntegrationTest extends AbstractUnitTest {

    private RunProcessReactor reactor;

    @Test
    public void shouldGetSomeTextAfterRunningSystemProcess() throws IOException, InterruptedException {
        // given
        givenRunProcessReactor();

        // when
        String processResult = reactor.runProcess("ver");

        // then
        Assertions.assertThat(processResult).contains("Windows");

    }

    private void givenRunProcessReactor() {
        reactor = new RunProcessReactor();
    }
}
