package org.reactor.filesystem.event;

import com.google.common.base.Function;
import org.reactor.event.ReactorEvent;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;
import java.nio.file.Path;

public class DirectoryChangedEvent implements ReactorEvent {

    public static final Function<DirectoryChangedEvent, ReactorResponse> TO_RESPONSE = new Function<DirectoryChangedEvent, ReactorResponse>() {

        @Override
        public ReactorResponse apply(DirectoryChangedEvent input) {
            return new StringReactorResponse("File changed: %s", input.changedPath.toFile().getAbsolutePath());
        }
    };

    private final Path changedPath;

    public DirectoryChangedEvent(Path changedPath) {
        this.changedPath = changedPath;
    }
}
