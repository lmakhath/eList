package com.elist.knormal.beans;

import java.util.ArrayList;
import java.util.List;

public class BeanList {

    private static BeanList instance;
    private List<ShopriteBean> list;

    private BeanList() {
        list = new ArrayList<>();
    }

    public static BeanList getInstance() {
        if (instance == null)
            instance = new BeanList();
        return instance;
    }

    public void setBean(List<ShopriteBean> list) {
        this.list = list;
    }

    public List<ShopriteBean> getList() {
        return list;
    }
}
