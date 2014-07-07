package org.reactor.response;

import static com.google.common.collect.Iterables.transform;
import static org.reactor.request.parser.ReactorRequestParameterDefinition.TO_CMD_LINE_OPTION;
import java.io.PrintWriter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.reactor.Reactor;
import org.reactor.request.parser.ReactorRequestParameterDefinition;

public class CommandHelpResponse extends StringReactorResponse {

    private static final int TEXT_WIDTH = 100;

    private static final String HELP_HEADER = "Available parameters:";
    private static final String HELP_FOOTER = null;
    private static final boolean AUTO_USAGE = true;
    private static final int LEFT_PAD = 3;
    private static final int DESC_PAD = 1;

    private final Reactor reactor;
    private final Iterable<ReactorRequestParameterDefinition> arguments;

    private HelpFormatter helpFormatter;

    public CommandHelpResponse(String reason, Reactor reactor, Iterable<ReactorRequestParameterDefinition> arguments) {
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
        transform(arguments, TO_CMD_LINE_OPTION).forEach(commandLineOptions::addOption);
        helpFormatter.printHelp(printWriter, TEXT_WIDTH, reactor.getTriggeringExpression(), HELP_HEADER, commandLineOptions, LEFT_PAD, DESC_PAD, HELP_FOOTER, AUTO_USAGE);
    }
}
