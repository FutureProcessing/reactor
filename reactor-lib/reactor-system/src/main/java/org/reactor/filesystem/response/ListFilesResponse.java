package org.reactor.filesystem.response;

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
        return new ListElementFormatter<File>() {

            @Override
            public String formatListElement(long elementIndex, File listElement) {
                return format("%s. %s", elementIndex, listElement.getName());
            }
        };
    }
}
