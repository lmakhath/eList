package com.elist.knormal;

import com.elist.knormal.beans.*;
import com.elist.knormal.client.ERestClient;
import com.elist.knormal.db.ProductJDBC;
import io.javalin.http.Context;
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

        List<ShopBean> beanList = BeanList.getInstance().getList();
        context.json(BeanList.getInstance().getList());
    }

    public static void postItems() {
        List<ShopBean> itemsList = BeanList.getInstance().getList();
        List<ShopriteRequestBean> requestBean = new ArrayList<>();

        for (ShopBean shopBean : itemsList) {
            requestBean.add(new ShopriteRequestBean(shopBean.getCode(),
                    shopBean.getName(), shopBean.getPrice(), false));
        }
        ERestClient.getInstance().doPostRequest(SR_URL, "Shoprite", requestBean);
    }

    public static void compare(Context context) {
        System.out.println(context.body());


    }

    public static void compareTest(Context context) {
        System.out.println(context.body());
        List<Items> itemsList = new ArrayList<>();
        List<Shops> shopsList = new ArrayList<>();

        Items items = new Items();
        items.setName("Tomato");
        items.setAmount("10.00");
        itemsList.add(items);

        Items items2 = new Items();
        items2.setName("Tomato");
        items2.setAmount("10.00");
        itemsList.add(items2);

        Shops shops = new Shops();
        shops.setName("Shoprite");
        shops.setItems(itemsList);

        shopsList.add(shops);

        Shops shops2 = new Shops();
        shops2.setName("Pick n pay");
        shops2.setItems(itemsList);

        shopsList.add(shops2);

        Shops shops3 = new Shops();
        shops3.setName("Woolies");
        shops3.setItems(itemsList);

        shopsList.add(shops3);

        Shops shops4 = new Shops();
        shops4.setName("Makro");
        shops4.setItems(itemsList);

        shopsList.add(shops4);

        Shops shops5 = new Shops();
        shops5.setName("Checkers");
        shops5.setItems(itemsList);

        shopsList.add(shops5);

        Shops shops6 = new Shops();
        shops6.setName("Spar");
        shops6.setItems(itemsList);

        shopsList.add(shops6);

        Shops shops7 = new Shops();
        shops7.setName("Boxer");
        shops7.setItems(itemsList);

        shopsList.add(shops7);

        CompareBean bean = new CompareBean();
        bean.setShops(shopsList);

        bean.setError(false);

        context.json(bean);
    }
}
