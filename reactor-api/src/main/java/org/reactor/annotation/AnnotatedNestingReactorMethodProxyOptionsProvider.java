package org.reactor.annotation;

import static com.google.common.collect.Lists.newLinkedList;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import org.reactor.request.parser.ReactorRequestParameterDefinition;

public class AnnotatedNestingReactorMethodProxyOptionsProvider {

    public final Iterable<ReactorRequestParameterDefinition> provideReactorMethodProxyOptions(Method objectMethod) {
        List<ReactorRequestParameterDefinition> parameterDefinitions = newLinkedList();

        Annotation[][] parameterAnnotations = objectMethod.getParameterAnnotations();
        Class<?>[] parameterTypes = objectMethod.getParameterTypes();
        int annotationIndex = 0;
        for (Annotation[] annotations : parameterAnnotations) {
            Class<?> parameterType = parameterTypes[annotationIndex++];
            for (Annotation annotation : annotations) {
                if (annotation instanceof ReactorRequestParameter) {
                    collectOption(parameterDefinitions, (ReactorRequestParameter) annotation, parameterType);
                }
            }
        }
        return parameterDefinitions;
    }

    private void collectOption(List<ReactorRequestParameterDefinition> parameterDefinitions,
                               ReactorRequestParameter parameterAnnotation, Class<?> parameterType) {
        parameterDefinitions.add(new ReactorRequestParameterDefinition(parameterAnnotation.name(),
            parameterAnnotation.shortName(), parameterAnnotation.required(), parameterType));
    }
}
