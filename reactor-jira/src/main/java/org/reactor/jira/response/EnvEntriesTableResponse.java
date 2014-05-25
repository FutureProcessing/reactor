package org.reactor.jira.response;

import static com.google.common.collect.Iterables.partition;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static java.lang.String.format;

import com.google.common.base.Function;

import org.reactor.response.table.TableReactorResponse;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnvEntriesTableResponse extends TableReactorResponse {

    public static final int MAX_VALUE_LENGTH = 20;
    public static final int PAGE = 0;
    public static final int SIZE = 10;

    private Set<Map.Entry<String, String>> envEntries = System.getenv().entrySet();

    public EnvEntriesTableResponse() {
        super("Environment variables");
        addHeaders("Key", "Value");
        setData(transform(newArrayList(partition(envEntries, SIZE)).get(PAGE),
            new Function<Map.Entry<String, String>, List<Object>>() {

                @Override
                public List<Object> apply(Map.Entry<String, String> input) {
                    String propertyValue = input.getValue();
                    if (propertyValue.length() > MAX_VALUE_LENGTH) {
                        propertyValue = propertyValue.substring(0, MAX_VALUE_LENGTH) + "...";
                    }
                    return newArrayList(input.getKey(), (Object) propertyValue);
                }
            }));
    }

    @Override
    protected void printFooter(PrintWriter printWriter) {
        printWriter.print(format("Total elements: %s, Current page: %s", envEntries.size(), PAGE));
    }
}
