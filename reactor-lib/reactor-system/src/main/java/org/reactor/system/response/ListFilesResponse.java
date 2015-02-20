package org.reactor.system.response;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.io.FilenameFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

import java.io.File;
import java.util.List;

public class ListFilesResponse extends ListReactorResponse<File> {

    private static final String WILDCARD_MATCH_ALL = "*";
    private final transient FilenameFilter filter;
    private final File directory;
    private final List<File> files;

    public ListFilesResponse(File directory, String fileNameMask) {
        this.filter = createFilter(fileNameMask);
        this.directory = directory;
        this.files = asList(directory.listFiles(filter));
    }

    private FilenameFilter createFilter(String fileNameMask) {
        return new WildcardFileFilter(firstNonNull(fileNameMask, WILDCARD_MATCH_ALL));
    }

    @Override
    public String toConsoleOutput() {
        return format("Directory: %s\nFiles: %s", directory, files);
    }

    public File getDirectory() {
        return directory;
    }

    public Iterable<File> getFiles() {
        return files;
    }

    @Override
    protected Iterable<File> getElements() {
        return asList(directory.listFiles(filter));
    }

    @Override
    protected ListElementFormatter<File> getElementFormatter() {
        return (elementIndex, listElement) -> format("%s. %s", elementIndex, listElement.getName());
    }
}
