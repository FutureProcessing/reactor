package org.reactor.utils;

import static com.google.common.primitives.Primitives.isWrapperType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtils {

    private final static Logger LOG = LoggerFactory.getLogger(ClassUtils.class);

    public static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || isWrapperType(type) || type.equals(String.class);
    }

    public static Class<?> getFirstGenericParameterType(Method method) {
        if (hasNoArguments(method)) {
            return null;
        }
        Type type = method.getGenericParameterTypes()[0];
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
        return Object.class;
    }

    private static boolean hasNoArguments(Method method) {
        Type[] types = method.getGenericParameterTypes();
        return types.length == 0;
    }

    public static <TYPE, OUTPUT> OUTPUT tryCall(Object subject, Class<TYPE> tryType,
                                                PossibleTypeAction<TYPE, OUTPUT> possibleTypeAction) {
        if (tryType.isAssignableFrom(subject.getClass())) {
            TYPE typedSubject = (TYPE) subject;
            return possibleTypeAction.invokeAction(typedSubject);
        }
        LOG.warn("Given subject {} is not of type {}", subject.getClass().getName(), tryType.getName());
        return null;
    }

    public static <TYPE, OUTPUT> OUTPUT tryCall(TYPE input, PossibleTypeAction<TYPE, OUTPUT> possibleTypeAction) {
        return possibleTypeAction.invokeAction(input);
    }

    public interface PossibleTypeAction<INPUT, OUTPUT> {

        OUTPUT invokeAction(INPUT subject);
    }

}
