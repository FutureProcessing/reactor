package org.reactor.request.parser;

import org.reactor.annotation.ReactorRequestParameter;

public class ReactorRequestComplexDataWithAnnotations {

    @ReactorRequestParameter(shortName = "f1", required = true)
    private String field1;

    @ReactorRequestParameter(shortName = "f2", required = true)
    private boolean field2;

    @ReactorRequestParameter(shortName = "f3", required = true)
    private int field3;


    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public boolean isField2() {
        return field2;
    }

    public void setField2(boolean field2) {
        this.field2 = field2;
    }

    public int getField3() {
        return field3;
    }

    public void setField3(int field3) {
        this.field3 = field3;
    }
}
