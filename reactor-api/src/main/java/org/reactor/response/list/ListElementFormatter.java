package org.reactor.response.list;

public interface ListElementFormatter<T> {

    public String formatListElement(long elementIndex, T listElement);

}
