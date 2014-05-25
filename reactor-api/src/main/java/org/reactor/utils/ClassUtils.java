package org.reactor.utils;

import static java.lang.String.format;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ClassUtils {

    private final static Logger LOG = LoggerFactory.getLogger(ClassUtils.class);

    public static <TYPE extends Object> TYPE newInstance(String type, Class<? extends TYPE> superType)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class foundClass = Class.forName(type);
        if (superType.isAssignableFrom(foundClass)) {
            return (TYPE) foundClass.newInstance();
        } else {
            throw new ClassCastException(format("Wrong class type: %s, should be instance of %s", foundClass.getName(),
                superType.getName()));
        }
    }

    public static <TYPE extends Object, OUTPUT extends Object> OUTPUT tryCall(Object subject,
                                                                               Class<TYPE> tryType,
                                                                               PossibleTypeAction<TYPE, OUTPUT> possibleTypeAction) {
        if (tryType.isAssignableFrom(subject.getClass())) {
            TYPE typedSubject = (TYPE) subject;
            return possibleTypeAction.invokeAction(typedSubject);
        }
        LOG.warn("Given subject {} is not of type {}", subject.getClass().getName(), tryType.getName());
        return null;
    }

    public static <TYPE extends Object, OUTPUT extends Object> OUTPUT tryCall(TYPE input,
                                                                               PossibleTypeAction<TYPE, OUTPUT> possibleTypeAction) {
        return possibleTypeAction.invokeAction(input);
    }

    public interface PossibleTypeAction<INPUT, OUTPUT> {
        OUTPUT invokeAction(INPUT subject);
    }

}
