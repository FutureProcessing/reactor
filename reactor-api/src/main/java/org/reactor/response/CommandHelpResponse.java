package org.reactor.response;

import static com.google.common.collect.Iterables.transform;
import static org.reactor.request.parser.ReactorRequestInputParameterDefinition.TO_CMD_LINE_OPTION;

import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.reactor.Reactor;
import org.reactor.discovery.ReactorParametersDiscoveringVisitor;
import org.reactor.request.parser.ReactorRequestInputParameterDefinition;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.RendererWriterAdapter;

import com.google.common.annotations.VisibleForTesting;

public class CommandHelpResponse implements ReactorResponse {

    private static final int TEXT_WIDTH = 100;

    private static final String HELP_HEADER = "Available parameters:";
    private static final String HELP_FOOTER = null;
    private static final boolean AUTO_USAGE = true;
    private static final int LEFT_PAD = 3;
    private static final int DESC_PAD = 1;

    private final List<ReactorRequestInputParameterDefinition> arguments;
    private final String triggeringExpression;
    private final String responseHeader;

    private HelpFormatter helpFormatter;

    public CommandHelpResponse(String header, Reactor reactor) {
        triggeringExpression = reactor.getTriggeringExpression();
        arguments = readArguments(reactor);
        responseHeader = header;

        initializeHelpFormatter();
    }

    private List<ReactorRequestInputParameterDefinition> readArguments(Reactor reactor) {
        ReactorParametersDiscoveringVisitor parametersVisitor = new ReactorParametersDiscoveringVisitor();
        reactor.accept(parametersVisitor);
        return parametersVisitor.getParameters();
    }

    private void initializeHelpFormatter() {
        helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(TEXT_WIDTH);
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) {
        responseRenderer.renderHeadLine(responseHeader);

        Options commandLineOptions = new Options();
        transform(arguments, TO_CMD_LINE_OPTION).forEach(commandLineOptions::addOption);
        printHelp(helpFormatter, new PrintWriter(new RendererWriterAdapter(responseRenderer)), triggeringExpression,
            commandLineOptions);
    }

    private void printHelp(HelpFormatter helpFormatter, PrintWriter printWriter, String triggeringExpression,
                           Options commandLineOptions) {
        helpFormatter.printHelp(printWriter, TEXT_WIDTH, triggeringExpression, HELP_HEADER, commandLineOptions,
            LEFT_PAD, DESC_PAD, HELP_FOOTER, AUTO_USAGE);
    }

    @VisibleForTesting
    void setArguments(List<ReactorRequestInputParameterDefinition> arguments) {
        this.arguments.clear();
        this.arguments.addAll(arguments);
    }
}
