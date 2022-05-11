package com.timetraveling.models;

import java.io.Serializable;

public class PatchBody implements Serializable {
    private String operation;
    private String value;
    private String attribute;

    public PatchBody(String operation, String value, String attribute) {
        this.operation = operation;
        this.value = value;
        this.attribute = attribute;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
