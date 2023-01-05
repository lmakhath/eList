package com.elist.knormal.beans;

import java.util.List;

public class Shops {
    private String name;
    private List<Items> items;

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<Items> getItems() {
        return items;
    }
}
