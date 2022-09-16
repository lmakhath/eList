package com.elist.knormal.beans;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ShopriteJsonBean {
    private static ShopriteJsonBean instance;
    private List<JSONObject> jsonObjectList;
    private List<String> codes;

    private ShopriteJsonBean() {
        jsonObjectList = new ArrayList<>();
        codes = new ArrayList<>();
    }

    public static ShopriteJsonBean getInstance() {
        if(instance == null)
            instance = new ShopriteJsonBean();
        return instance;
    }

    public void addJsonObject(JSONObject jsonObject) {
        jsonObjectList.add(jsonObject);
        codes.add((String)jsonObject.get("code"));
    }

    public List<JSONObject> getJsonObjectList() {
        return jsonObjectList;
    }

    public List<String> getCodes() {
        return codes;
    }
}
