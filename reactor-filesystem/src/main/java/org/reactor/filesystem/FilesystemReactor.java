package org.reactor.filesystem;

import static java.lang.String.format;
import static org.reactor.filesystem.event.DirectoryChangedEvent.TO_RESPONSE;

import org.reactor.AbstractNestingReactor;
import org.reactor.ReactorProcessingException;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.event.EventProducingReactor;
import org.reactor.event.ReactorEventConsumerFactory;
import org.reactor.filesystem.event.DirectoryChangedEvent;
import org.reactor.filesystem.request.FileNameMatchRequest;
import org.reactor.filesystem.request.FileNameRequest;
import org.reactor.filesystem.response.ListFilesResponse;
import org.reactor.filesystem.response.RemoveFileResponse;
import org.reactor.filesystem.response.TouchFileResponse;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.transport.ReactorMessageTransportInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@ReactOn(value = "fs",
         description = "Does some basic filesystem manipulation and informs about changes in given directory")
public class FilesystemReactor extends AbstractNestingReactor implements EventProducingReactor {

    private final static Logger LOG = LoggerFactory.getLogger(FilesystemReactor.class);

    private File directory;
    private boolean directoryListener;

    @ReactOn(value = "ls", description = "Prints list of files in given folder")
    public ReactorResponse listFiles(ReactorRequest<FileNameMatchRequest> reactorRequest) {
        return new ListFilesResponse(directory, reactorRequest.getRequestData().getFileNameMask());
    }

    @ReactOn(value = "rm", description = "Removes file with given name")
    public ReactorResponse removeFile(ReactorRequest<FileNameRequest> fileRequest) throws FileNotFoundException {
        File fileToRemove = new File(directory, fileRequest.getRequestData().getFileName());
        if (!fileToRemove.exists()) {
            throw new FileNotFoundException(fileRequest.getRequestData().getFileName());
        }
        return new RemoveFileResponse(fileRequest.getRequestData().getFileName(), fileToRemove.delete());
    }

    @ReactOn(value = "touch", description = "Does the same as linux 'touch' command on given file")
    public ReactorResponse touch(ReactorRequest<FileNameRequest> fileRequest) {
        try {
            File fileToTouch = new File(directory, fileRequest.getRequestData().getFileName());
            if (!fileToTouch.exists()) {
                new FileOutputStream(fileToTouch).close();
                return new TouchFileResponse(fileRequest.getRequestData().getFileName(), true);
            }
            boolean touched = fileToTouch.setLastModified(System.currentTimeMillis());
            return new TouchFileResponse(fileRequest.getRequestData().getFileName(), touched);
        } catch (IOException e) {
            throw new ReactorProcessingException(e);
        }
    }

    @Override
    public void initNestingReactor(ReactorProperties properties) {
        initFilesystemReactor(new FilesystemReactorProperties(properties));
    }

    private void initFilesystemReactor(FilesystemReactorProperties filesystemReactorProperties) {
        initDirectoryHandle(filesystemReactorProperties);
        initDirectoryListenerFlag(filesystemReactorProperties);
    }

    private void initDirectoryHandle(FilesystemReactorProperties filesystemReactorProperties) {
        directory = new File(filesystemReactorProperties.getListeningDirectory());
        validateDirectory();
    }

    private void initDirectoryListenerFlag(FilesystemReactorProperties filesystemReactorProperties) {
        directoryListener = filesystemReactorProperties.isListenerEnabled();
    }

    private void validateDirectory() {
        if (!directory.exists()) {
            throw new ReactorMessageTransportInitializationException(format("Given directory can not be found: %s",
                directory.getAbsolutePath()));
        }
        if (!directory.isDirectory()) {
            throw new ReactorMessageTransportInitializationException(format("Given location is not a directory: %s",
                directory.getAbsolutePath()));
        }
    }

    @Override
    public void initReactorEventConsumers(ReactorEventConsumerFactory factory) {
        if (!directoryListener) {
            return;
        }
        try {
            LOG.debug("Initializing directory listener");
            new Thread(new FilesystemMonitorRunnable(directory, factory.createEventConsumer(
                DirectoryChangedEvent.class, TO_RESPONSE))).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
