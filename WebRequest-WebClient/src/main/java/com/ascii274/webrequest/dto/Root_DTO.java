package com.ascii274.webrequest.dto;

import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class Root_DTO<T> {
    private int count;
    private T[] results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T[] getResults() {
        return results;
    }

    public void setResults(T[] results) {
        this.results = results;
//        this.results = (T[]) Stream.of(results).toArray();
    }
}
