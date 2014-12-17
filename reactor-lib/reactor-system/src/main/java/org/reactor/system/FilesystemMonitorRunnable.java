package org.reactor.system;

import static java.nio.file.StandardWatchEventKinds.*;

import org.reactor.event.ReactorEventConsumer;
import org.reactor.system.event.DirectoryChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FilesystemMonitorRunnable implements Runnable {

    private final static Logger LOG = LoggerFactory.getLogger(FilesystemMonitorRunnable.class);

    private final File watchingDirectory;
    private final ReactorEventConsumer<DirectoryChangedEvent> directoryChangedReactorEventConsumer;
    private WatchService watchService;

    public FilesystemMonitorRunnable(File watchingDirectory,
                                     ReactorEventConsumer<DirectoryChangedEvent> directoryChangedReactorEventConsumer)
            throws IOException {
        this.watchingDirectory = watchingDirectory;
        this.directoryChangedReactorEventConsumer = directoryChangedReactorEventConsumer;
        initializeWatchService();
    }

    private void initializeWatchService() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            watchingDirectory.toPath().register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                    directoryChangedReactorEventConsumer.consumeEvent(new DirectoryChangedEvent(pathEvent.context()));
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            LOG.error("An error occurred while analyzing directory changes", e);
        }
    }
}
