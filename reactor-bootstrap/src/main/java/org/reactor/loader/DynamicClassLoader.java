package org.reactor.loader;

import static com.google.common.io.Files.fileTreeTraverser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicClassLoader extends URLClassLoader {

    private final static Logger LOG = LoggerFactory.getLogger(DynamicClassLoader.class);

    public DynamicClassLoader(String classesLocation) {
        super(((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs());
        fileTreeTraverser().preOrderTraversal(new File(classesLocation)).forEach(this::tryToRegisterFile);
    }

    private void tryToRegisterFile(File file) {
        try {
            registerFile(file);
        } catch (MalformedURLException e) {
            LOG.error("Could not register file", e);
        }
    }

    private void registerFile(File file) throws MalformedURLException {
        addURL(file.toURI().toURL());
    }
}
