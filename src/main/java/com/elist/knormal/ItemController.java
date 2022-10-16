package com.elist.knormal;

import com.elist.knormal.beans.BeanList;
import com.elist.knormal.beans.Results;
import com.elist.knormal.beans.ShopriteBean;
import com.elist.knormal.beans.ShopriteRequestBean;
import com.elist.knormal.client.ERestClient;
import com.elist.knormal.db.ProductJDBC;
import io.javalin.http.Context;

import javax.enterprise.inject.spi.Bean;
import java.util.ArrayList;
import java.util.List;

public class ItemController {
    private static String SR_URL = "https://shielded-wildwood-92316.herokuapp.com/upload_items";
    /*private static String SR_URL = "http://localhost:5000/upload_items";*/
    public static void refreshItemList(Context context) {
        if (ProductJDBC.getInstance().selectAllShopriteItems())
            context.json(new Results("SUCCESS", "Successful"));
        else
            context.json(new Results("ERROR", "Failed to update db items"));
    }

    public static void getItems(Context context) {
        context.json(BeanList.getInstance().getList());
    }

    public static void postItems() {
        List<ShopriteBean> itemsList = BeanList.getInstance().getList();
        List<ShopriteRequestBean> requestBean = new ArrayList<>();

        for (ShopriteBean shopriteBean : itemsList) {
            requestBean.add(new ShopriteRequestBean(shopriteBean.getCode(),
                    shopriteBean.getName(), shopriteBean.getPrice(), false));
        }
        ERestClient.getInstance().doPostRequest(SR_URL, "Shoprite", requestBean);
    }
}
