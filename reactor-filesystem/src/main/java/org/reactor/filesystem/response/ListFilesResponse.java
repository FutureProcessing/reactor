package org.reactor.filesystem.response;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

import java.io.File;

public class ListFilesResponse extends ListReactorResponse<File> {

    private final File directory;

    public ListFilesResponse(File directory) {
        this.directory = directory;
    }

    @Override
    protected Iterable<File> getElements() {
        return asList(directory.listFiles());
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
