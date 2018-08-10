package com.epam.au.service.filter;

import java.util.List;

public class Criteria {
    String mode;
    List<Object> values;

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
}
