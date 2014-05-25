package org.reactor.response.table;

import static com.bethecoder.ascii_table.ASCIITable.getInstance;
import static com.google.common.collect.Lists.newArrayList;
import com.bethecoder.ascii_table.ASCIITableHeader;
import com.bethecoder.ascii_table.spec.IASCIITableAware;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;
import org.reactor.response.StringReactorResponse;

public class TableReactorResponse extends StringReactorResponse implements IASCIITableAware {

    private List<ASCIITableHeader> headers = newArrayList();

    private List<List<Object>> data = newArrayList();

    public TableReactorResponse(String header) {
        super(header);
    }

    @Override
    protected void printResponse(PrintWriter printWriter) {
        String table = getInstance().getTable(this);
        BufferedReader tableReader = new BufferedReader(new StringReader(table));
        String tableLine;
        try {
            while ((tableLine = tableReader.readLine()) != null) {
                printWriter.println(tableLine);
            }
        } catch (IOException e) {
            return;
        }
        printFooter(printWriter);
    }

    protected void printFooter(PrintWriter printWriter) {

    }

    @Override
    public List<ASCIITableHeader> getHeaders() {
        return headers;
    }

    @Override
    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    @Override
    public String formatData(ASCIITableHeader header, int row, int col, Object data) {
        return null;
    }

    public void addHeaders(String... headers) {
        for (String header : headers) {
            this.headers.add(new ASCIITableHeader(header));
        }
    }
}
