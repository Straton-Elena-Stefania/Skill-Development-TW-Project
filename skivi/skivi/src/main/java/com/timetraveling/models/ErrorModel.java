package com.timetraveling.models;

import java.io.Serializable;

public class ErrorModel implements Serializable {
    private String errorMessage;
    private int status;

    public ErrorModel(String errorMessage, int status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
