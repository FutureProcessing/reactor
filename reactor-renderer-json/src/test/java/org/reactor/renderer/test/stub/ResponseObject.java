package org.reactor.renderer.test.stub;

import org.reactor.response.ReactorResponse;

public class ResponseObject implements ReactorResponse {

    private String header;
    private Long longValue;
    private String string;
    private Double doubleValue;
    private transient Object transientValue;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Object getTransientValue() {
        return transientValue;
    }

    public void setTransientValue(Object transientValue) {
        this.transientValue = transientValue;
    }
}
