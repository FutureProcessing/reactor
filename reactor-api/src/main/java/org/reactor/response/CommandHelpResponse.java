package org.reactor.response;

import static com.google.common.collect.Iterables.transform;
import static org.reactor.request.parser.ReactorRequestParameterDefinition.TO_CMD_LINE_OPTION;
import java.io.PrintWriter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.reactor.Reactor;
import org.reactor.request.parser.ReactorRequestParameterDefinition;

public class CommandHelpResponse extends StringReactorResponse {

    public static final int TEXT_WIDTH = 100;
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
        helpFormatter.printUsage(printWriter, TEXT_WIDTH, reactor.getTriggeringExpression(), commandLineOptions);
    }

}
