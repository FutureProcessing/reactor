package org.reactor.system.process;

import org.junit.Before;
import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.lang.System.getProperty;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

public class RunProcessReactorWindowsIntegrationTest extends AbstractUnitTest {

    private RunProcessReactor reactor;

    @Before
    public void runIfWindows() {
        assumeTrue(getProperty("os.name").contains("Windows"));
    }

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
