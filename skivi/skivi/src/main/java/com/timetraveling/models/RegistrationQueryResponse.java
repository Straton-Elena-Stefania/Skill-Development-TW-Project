package com.timetraveling.models;

import java.io.Serializable;

public class RegistrationQueryResponse implements Serializable {
    private String emailError = "null";
    private String usernameError = "null";
    private String passwordError = "null";

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }
}
