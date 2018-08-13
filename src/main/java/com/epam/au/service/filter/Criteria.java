package com.epam.au.service.filter;

import java.util.LinkedList;
import java.util.List;

public class Criteria {
    String mode;
    List<Object> values;

    public Criteria() {
        values = new LinkedList<>();
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public void addValue(Object value) {
        values.add(value);
    }
}
