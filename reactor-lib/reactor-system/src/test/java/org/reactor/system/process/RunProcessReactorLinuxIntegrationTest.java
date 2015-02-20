package org.reactor.system.process;

import org.junit.Before;
import org.junit.Test;
import org.reactor.AbstractUnitTest;

import static java.lang.System.getProperty;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

public class RunProcessReactorLinuxIntegrationTest extends AbstractUnitTest {

    private RunProcessReactor reactor;

    @Before
    public void runIfLinux() {
        assumeTrue(getProperty("os.name").contains("Linux"));
    }

    @Test
    public void shouldGetSomeTextAfterRunningSystemProcess() throws Exception{
        // given
        givenRunProcessReactor();

        // when
        String processResult = reactor.runProcess("uname", "-a");

        // then
        assertThat(processResult).contains("Linux");

    }

    private void givenRunProcessReactor() {
        reactor = new RunProcessReactor();
    }
}
