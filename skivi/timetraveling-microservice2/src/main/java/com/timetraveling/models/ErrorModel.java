package com.timetraveling.models;

import java.io.Serializable;

public class ErrorModel implements Serializable {
    private String errorMessage;
    private int status;
    private String errorLink;

    public ErrorModel(String errorMessage, int status, String errorLink) {
        this.errorMessage = errorMessage;
        this.status = status;
        this.errorLink = errorLink;
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

    public String getErrorLink() {
        return errorLink;
    }

    public void setErrorLink(String errorLink) {
        this.errorLink = errorLink;
    }
}
