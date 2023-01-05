package com.elist.knormal.beans;

import java.util.List;

public class CompareBean {
    private List<Shops> shops;
    private boolean error;
    private String message;

    public void setShops(List<Shops> shops) {
        this.shops = shops;
    }

    public List<Shops> getShops() {
        return shops;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean getError() {
        return error;
    }
}

