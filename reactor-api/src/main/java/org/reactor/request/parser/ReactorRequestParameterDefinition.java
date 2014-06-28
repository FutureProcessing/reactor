package org.reactor.request.parser;

import java.lang.reflect.Field;
import org.apache.commons.cli.Option;
import com.google.common.base.Function;

public class ReactorRequestParameterDefinition {

    public static final Function<ReactorRequestParameterDefinition, Option> TO_CMD_LINE_OPTION = new Function<ReactorRequestParameterDefinition, Option>() {

        private static final String DESCRIPTION_EMPTY = "";

        @Override
        public Option apply(ReactorRequestParameterDefinition input) {
            Option option = new Option(input.getShortName(), input.getName(), true, DESCRIPTION_EMPTY);
            option.setRequired(input.isRequired());
            option.setType(input.getType());
            return option;
        }
    };

    public static final Function<Field, ReactorRequestParameterDefinition> FROM_FIELD = new Function<Field, ReactorRequestParameterDefinition>() {

        @Override
        public ReactorRequestParameterDefinition apply(Field input) {
            return null;
            // TODO
        }
    }   ;

    private String name;
    private String shortName;
    private final Class<?> type;
    private boolean required;

    public ReactorRequestParameterDefinition(String name, String shortName, boolean required, Class<?> type) {
        this.name = name;
        this.shortName = shortName;
        this.required = required;
        this.type = type;
    }

    public String getShortName() {
        return shortName;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReactorRequestParameterDefinition that = (ReactorRequestParameterDefinition) o;

        if (required != that.required) return false;
        if (!name.equals(that.name)) return false;
        if (!shortName.equals(that.shortName)) return false;
        if (!type.equals(that.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + shortName.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (required ? 1 : 0);
        return result;
    }
}
