package com.elist.knormal.beans;

public class ShopriteRequestBean {
    private String code;
    private String name;
    private String price;
    private boolean promo;

    public ShopriteRequestBean(String code, String name, String price, boolean promo) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.promo = promo;
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

    public boolean isPromo() {
        return promo;
    }
}
