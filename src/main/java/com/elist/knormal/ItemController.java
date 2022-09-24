package com.elist.knormal;

import com.elist.knormal.beans.BeanList;
import com.elist.knormal.beans.Results;
import com.elist.knormal.db.ProductJDBC;
import io.javalin.http.Context;

import javax.enterprise.inject.spi.Bean;

public class ItemController {

    public static void refreshItemList(Context context) {
        if (ProductJDBC.getInstance().selectAllShopriteItems())
            context.json(new Results("SUCCESS", "Successful"));
        else
            context.json(new Results("ERROR", "Failed to update items"));
    }

    public static void getItems(Context context) {
        context.json(BeanList.getInstance().getList());
    }
}
