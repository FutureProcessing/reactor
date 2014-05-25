package org.reactor.annotation;

import com.google.common.base.Predicate;
import java.lang.reflect.Method;

public class AnnotatedNestingReactorMethodFilter implements Predicate<Method> {

    private final String methodName;

    public AnnotatedNestingReactorMethodFilter(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public boolean apply(Method input) {
        return input.getName().equals(methodName) && input.isAnnotationPresent(ReactOn.class);
    }
}
