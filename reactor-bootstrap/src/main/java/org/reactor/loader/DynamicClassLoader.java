package org.reactor.loader;

import static com.google.common.io.Files.fileTreeTraverser;
import static com.google.common.io.Files.isFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

public class DynamicClassLoader extends URLClassLoader {

    private final static Logger LOG = LoggerFactory.getLogger(DynamicClassLoader.class);

    private final static Function<File, URL> TO_URL = file -> {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            LOG.error("An error occurred while transforming file to URL.", e);
            return null;
        }
    };

    public DynamicClassLoader(String classesLocation) {
        super(fileTreeTraverser().preOrderTraversal(new File(classesLocation)).filter(isFile()).transform(TO_URL)
            .toArray(URL.class), DynamicClassLoader.class.getClassLoader());
    }
}
