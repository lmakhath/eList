package com.elist.knormal.beans;

public class Results {
    private String result;
    private String message;

    public Results(String result, String message) {
        this.message = message;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
