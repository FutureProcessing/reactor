package org.reactor.annotation;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newLinkedList;
import static org.apache.commons.beanutils.ConvertUtils.convert;
import static org.reactor.annotation.AnnotatedNestingReactorMethodProxyOption.TO_CMD_LINE_OPTION;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.reactor.request.ReactorRequest;

public class AnnotatedNestingReactorMethodProxyArgumentsProvider {

    private final Iterable<AnnotatedNestingReactorMethodProxyOption> objectMethodOptions;

    public AnnotatedNestingReactorMethodProxyArgumentsProvider(Iterable<AnnotatedNestingReactorMethodProxyOption> objectMethodOptions) {
        this.objectMethodOptions = objectMethodOptions;
    }

    public Iterable<Object> provideReactorMethodProxyArguments(ReactorRequest reactorRequest) throws ParseException {
        List<Object> methodArguments = newLinkedList();
        CommandLine commandLine = parseCommandLine(reactorRequest.getArguments());
        for (AnnotatedNestingReactorMethodProxyOption objectMethodOption : objectMethodOptions) {
            String argumentValue = commandLine.getOptionValue(objectMethodOption.getName());
            methodArguments.add(convert(argumentValue, objectMethodOption.getType()));
        }
        return methodArguments;
    }

    private CommandLine parseCommandLine(String[] arguments) throws ParseException {
        Options options = new Options();
        for (Option commandOption : transform(objectMethodOptions, TO_CMD_LINE_OPTION)) {
            options.addOption(commandOption);
        }
        return new PosixParser().parse(options, arguments);
    }
}
