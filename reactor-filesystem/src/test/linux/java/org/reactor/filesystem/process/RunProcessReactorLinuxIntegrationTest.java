package org.reactor.filesystem.process;

import java.io.IOException;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.reactor.AbstractUnitTest;

public class RunProcessReactorLinuxIntegrationTest extends AbstractUnitTest {

    private RunProcessReactor reactor;

    @Test
    public void shouldGetSomeTextAfterRunningSystemProcess() throws IOException, InterruptedException {
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
