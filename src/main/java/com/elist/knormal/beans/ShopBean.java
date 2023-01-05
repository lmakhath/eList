package com.elist.knormal.beans;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ShopBean {

    private String code;
    private String name;
    private String price;
    private Timestamp timestamp;
    private ShopEnums shop;

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setShop(ShopEnums shop) {
        this.shop = shop;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public ShopEnums getShop() {
        return shop;
    }
}
