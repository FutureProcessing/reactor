package org.reactor.request.parser;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.transform;
import static java.util.Arrays.asList;
import static org.apache.commons.beanutils.ConvertUtils.convert;
import static org.reactor.request.parser.ReactorRequestParameterDefinition.TO_CMD_LINE_OPTION;
import static org.reactor.utils.ClassUtils.isPrimitive;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.reactor.annotation.ReactorRequestParameter;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequest;
import org.reactor.request.ReactorRequestInput;
import org.reactor.request.ReactorRequestParsingException;

public class ReactorRequestComplexDataParser<T> extends AbstractReactorRequestDataParser<T> {

    private static final Function<Field, ReactorRequestParameterDefinition> FROM_FIELD = new Function<Field, ReactorRequestParameterDefinition>() {

        @Override
        public ReactorRequestParameterDefinition apply(Field field) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            ReactorRequestParameterDefinition parameterDefinition = new ReactorRequestParameterDefinition(fieldName,
                fieldName, false, fieldType);
            processAnnotations(field, parameterDefinition);
            return parameterDefinition;
        }

        private void processAnnotations(Field field, ReactorRequestParameterDefinition parameterDefinition) {
            if (field.isAnnotationPresent(ReactorRequestParameter.class)) {
                ReactorRequestParameter parameterAnnotation = field.getAnnotation(ReactorRequestParameter.class);
                parameterDefinition.setShortName(parameterAnnotation.shortName());
                parameterDefinition.setRequired(parameterAnnotation.required());
            }
        }
    };

    private static final Predicate<Field> PRIMITIVE_WITH_ANNOTATION = new Predicate<Field>() {

        @Override
        public boolean apply(Field field) {
            return isPrimitive(field.getType()) && hasAnnotation(field);
        }

        private boolean hasAnnotation(Field field) {
            return field.isAnnotationPresent(ReactorRequestParameter.class);
        }
    };

    private final Class<T> dataType;
    private final List<ReactorRequestParameterDefinition> requestParameters;

    public ReactorRequestComplexDataParser(Class<T> dataType) {
        this.dataType = dataType;
        this.requestParameters = from(asList(dataType.getDeclaredFields())).filter(PRIMITIVE_WITH_ANNOTATION).transform(FROM_FIELD)
                .toList();
    }

    public ReactorRequest<T> parseRequestWithData(String sender, String trigger, ReactorRequestInput requestInput) {
        T requestData = prepareRequestDataInstance();

        CommandLine commandLine = parseCommandLine(requestInput.getArguments());
        requestParameters.forEach(requestParameterDefinition -> {
            String parameterValue = commandLine.getOptionValue(requestParameterDefinition.getName());
            setRequestDataParameter(requestData, requestParameterDefinition, parameterValue);
        });
        return new ReactorRequest<>(sender, trigger, requestData);
    }

    private void setRequestDataParameter(T requestData, ReactorRequestParameterDefinition requestParameterDefinition, String parameterValue) {
        try {
            BeanUtils.setProperty(requestData, requestParameterDefinition.getName(), convert(parameterValue, requestParameterDefinition.getType()));
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new ReactorRequestParsingException(e);
        }
    }

    private T prepareRequestDataInstance()  {
        try {
            // TODO very naive, do it better
            return dataType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ReactorRequestParsingException(e);
        }
    }

    private CommandLine parseCommandLine(String[] inputArguments) {
        Options commandLineOptions = prepareCommandLineOptions();
        try {
            return new PosixParser().parse(commandLineOptions, inputArguments);
        } catch (ParseException e) {
            throw new ReactorRequestParsingException(e);
        }
    }

    private Options prepareCommandLineOptions() {
        Options options = new Options();
        transform(requestParameters, TO_CMD_LINE_OPTION).forEach(options::addOption);
        return options;
    }

    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {
        requestParameters.forEach(topologyVisitor::visitReactorRequestParameter);
    }
}
