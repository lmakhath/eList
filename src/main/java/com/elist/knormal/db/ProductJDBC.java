package com.elist.knormal.db;

import com.elist.knormal.beans.BeanList;
import com.elist.knormal.beans.ShopBean;
import com.elist.knormal.beans.ShopEnums;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductJDBC {
    private Connection conn;
    private static ProductJDBC instance;
    private static final String SR_INSERT = "INSERT INTO shoprite_items(code, name, price, created_on) values (?,?,?,?)";
    private static final String SR_UPDATE = "UPDATE shoprite_items set name = ?, price = ? where code = ?";
    private static final String SELECT_SR_ITEMS = "Select * from shoprite_items";
    private static final String SELECT_MAKRO_ITEMS = "Select * from makro_items";
    private static final String SELECT_CHECKERS_ITEMS = "Select * from checkers_items";
    private static final String SELECT_BOXER_ITEMS = "Select * from boxer_items";
    private static final String SELECT_PNP_ITEMS = "Select * from pnp_items";
    private static final String SELECT_WOOLWORTHS_ITEMS = "Select * from woolworths_items";
    private static final String SELECT_SPAR_ITEMS = "Select * from spar_items";
    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/grocerylistdb";
    private List<ShopBean> list = new ArrayList<>();

    private ProductJDBC() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(POSTGRES_URL, "postgres", "19230M@rs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ProductJDBC getInstance() {
        if (instance == null) {
            instance = new ProductJDBC();
        }
        return instance;
    }

    public void insertShopriteItemsList(List<JSONObject> objectList) {
        for (JSONObject jo : objectList) {
            if (shopriteInsert(jo) == 0 && BeanList.getInstance().checkForUpdate(jo)) {
                updateShopriteItem(jo);
            }
        }
    }

    public boolean selectAllShopriteItems() {

        BeanList instance = BeanList.getInstance();
        int selectCount = 0;

        selectCount += selectItems(SELECT_SR_ITEMS, ShopEnums.SHOPRITE);
        selectCount += selectItems(SELECT_MAKRO_ITEMS, ShopEnums.MAKRO);
        selectCount += selectItems(SELECT_CHECKERS_ITEMS, ShopEnums.CHECKERS);
        selectCount += selectItems(SELECT_BOXER_ITEMS, ShopEnums.BOXER);
        selectCount += selectItems(SELECT_PNP_ITEMS, ShopEnums.PICKNPAY);
        selectCount += selectItems(SELECT_SPAR_ITEMS, ShopEnums.SPAR);
        selectCount += selectItems(SELECT_WOOLWORTHS_ITEMS, ShopEnums.WOOLWORTHS);
        instance.setBean(list);
        if (selectCount < 7)
            return false;
        return true;
    }

    private int selectItems(String statement, ShopEnums shop) {
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(statement);

            while (rs.next()) {
                ShopBean bean = new ShopBean();
                bean.setCode(rs.getString("code"));
                bean.setName(rs.getString("name"));
                bean.setPrice(rs.getString("price"));
                bean.setTimestamp(rs.getTimestamp("created_on"));
                bean.setShop(shop);
                list.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public int shopriteInsert(JSONObject jo) {
        int n = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(SR_INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, jo.getString("id"));
            pstmt.setString(2, jo.getString("name"));
            pstmt.setString(3, jo.getString("price"));
            pstmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));

            n = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    private int updateShopriteItem (JSONObject jo){
        int n = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(SR_UPDATE, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, jo.getString("name"));
            pstmt.setString(2, jo.getString("price"));
            pstmt.setString(3, jo.getString("id"));

            n = pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }
}
