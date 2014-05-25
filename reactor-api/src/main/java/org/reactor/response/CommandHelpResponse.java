package org.reactor.response;

import static com.google.common.collect.Iterables.transform;
import static org.reactor.annotation.AnnotatedNestingReactorMethodProxyOption.TO_CMD_LINE_OPTION;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.reactor.Reactor;
import org.reactor.annotation.AnnotatedNestingReactorMethodProxyOption;

import java.io.PrintWriter;

public class CommandHelpResponse extends StringReactorResponse {

    public static final int TEXT_WIDTH = 100;
    private final Reactor reactor;
    private final Iterable<AnnotatedNestingReactorMethodProxyOption> arguments;

    private HelpFormatter helpFormatter;

    public CommandHelpResponse(String reason, Reactor reactor, Iterable<AnnotatedNestingReactorMethodProxyOption> arguments) {
        super(reason);
        this.reactor = reactor;
        this.arguments = arguments;

        initializeHelpFormatter();
    }

    private void initializeHelpFormatter() {
        helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(TEXT_WIDTH);
    }

    @Override
    protected void printResponse(PrintWriter printWriter) {
        Options commandLineOptions = new Options();
        for (Option commandOption : transform(arguments, TO_CMD_LINE_OPTION)) {
            commandLineOptions.addOption(commandOption);
        }
        helpFormatter.printUsage(printWriter, TEXT_WIDTH, reactor.getTriggeringExpression(), commandLineOptions);
    }

}
