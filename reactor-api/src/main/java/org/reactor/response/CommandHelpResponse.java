package org.reactor.response;

import static com.google.common.collect.Iterables.transform;
import static org.reactor.request.parser.ReactorRequestParameterDefinition.TO_CMD_LINE_OPTION;
import com.google.common.annotations.VisibleForTesting;
import java.io.PrintWriter;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.reactor.Reactor;
import org.reactor.discovery.ReactorParametersDiscoveringVisitor;
import org.reactor.request.parser.ReactorRequestParameterDefinition;

public class CommandHelpResponse extends StringReactorResponse {

    private static final int TEXT_WIDTH = 100;

    private static final String HELP_HEADER = "Available parameters:";
    private static final String HELP_FOOTER = null;
    private static final boolean AUTO_USAGE = true;
    private static final int LEFT_PAD = 3;
    private static final int DESC_PAD = 1;

    private final List<ReactorRequestParameterDefinition> arguments;
    private final String triggeringExpression;

    private HelpFormatter helpFormatter;

    public CommandHelpResponse(String header, Reactor reactor) {
        super(header);
        triggeringExpression = reactor.getTriggeringExpression();
        arguments = readArguments(reactor);

        initializeHelpFormatter();
    }

    private List<ReactorRequestParameterDefinition> readArguments(Reactor reactor) {
        ReactorParametersDiscoveringVisitor parametersVisitor = new ReactorParametersDiscoveringVisitor();
        reactor.accept(parametersVisitor);
        return parametersVisitor.getParameters();
    }

    private void initializeHelpFormatter() {
        helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(TEXT_WIDTH);
    }

    @Override
    protected void printResponse(PrintWriter printWriter) {
        Options commandLineOptions = new Options();
        transform(arguments, TO_CMD_LINE_OPTION).forEach(commandLineOptions::addOption);
        printHelp(helpFormatter, printWriter, triggeringExpression, commandLineOptions);
    }

    private void printHelp(HelpFormatter helpFormatter, PrintWriter printWriter, String triggeringExpression, Options commandLineOptions) {
        helpFormatter.printHelp(printWriter, TEXT_WIDTH, triggeringExpression, HELP_HEADER, commandLineOptions, LEFT_PAD, DESC_PAD, HELP_FOOTER, AUTO_USAGE);
    }


    @VisibleForTesting
    void setArguments(List<ReactorRequestParameterDefinition> arguments) {
        this.arguments.clear();
        this.arguments.addAll(arguments);
    }
}
