package org.reactor.loader;

public interface DifferentClassLoaderOperation<T> {

    public static <T> T doInDifferentContextClassLoader(ClassLoader differentClassLoader,
                                                        DifferentClassLoaderOperation<T> operation) {
        Thread currentThread = Thread.currentThread();
        ClassLoader previousClassLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(differentClassLoader);
        T result = operation.run();
        currentThread.setContextClassLoader(previousClassLoader);
        return result;
    }

    T run();
}
