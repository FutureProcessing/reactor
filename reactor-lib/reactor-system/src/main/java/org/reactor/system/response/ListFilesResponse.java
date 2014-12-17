package org.reactor.system.response;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.io.FilenameFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

import java.io.File;

public class ListFilesResponse extends ListReactorResponse<File> {

    public static final String WILDCARD_MATCH_ALL = "*";
    private final File directory;
    private final FilenameFilter filter;

    public ListFilesResponse(File directory, String fileNameMask) {
        super("");
        this.directory = directory;
        this.filter = createFilter(fileNameMask);
    }

    private FilenameFilter createFilter(String fileNameMask) {
        return new WildcardFileFilter(firstNonNull(fileNameMask, WILDCARD_MATCH_ALL));
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
