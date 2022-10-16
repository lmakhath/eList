package com.elist.knormal.beans;

import org.json.JSONObject;

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

    public boolean checkForUpdate(JSONObject jObject) {
        ShopriteBean tmp = null;
        for (ShopriteBean bean : list) {
            if (bean.getCode().equalsIgnoreCase(jObject.getString("id"))) {
                tmp = bean;
            }
        }
        if (tmp != null) {
            if(!jObject.getString("name").equalsIgnoreCase(tmp.getName())
                    || !jObject.getString("price").equalsIgnoreCase(tmp.getPrice())) {
                ShopriteBean bean = new ShopriteBean();
                bean.setCode(tmp.getCode());
                bean.setName(jObject.getString("name"));
                bean.setPrice(jObject.getString("price"));
                bean.setTimestamp(tmp.getTimestamp());
                list.remove(tmp); // remove old bean
                list.add(bean); // add new updated bean
                return true;
            }
        }
        return false;
    }
}
