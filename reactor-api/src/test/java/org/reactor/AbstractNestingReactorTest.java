package org.reactor;

import org.junit.Test;
import org.mockito.Mock;

public class AbstractNestingReactorTest extends AbstractUnitTest {

    @Mock
    private AbstractNestingReactor nestingReactor;

    @Test
    public void shouldThrowAnExceptionWhenRegisteringItselfAsNestedReactor() {
        // then
        expectedException.expect(ReactorInitializationException.class);

        // when
        nestingReactor.registerNestedReactor(nestingReactor);
    }

}
