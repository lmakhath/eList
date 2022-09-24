package com.elist.knormal.db;

import com.elist.knormal.beans.BeanList;
import com.elist.knormal.beans.ShopriteBean;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductJDBC {
    private Connection conn;
    private static ProductJDBC instance;
    private static final String SHOPRITE_INSERT = "INSERT INTO shoprite_items(code, name, price, created_on) values (?,?,?,?)";
    private static final String SELECT_SHOPRITE_ITEMS = "Select * from shoprite_items";
    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/grocerylistdb";

    private ProductJDBC() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(POSTGRES_URL, "postgres","19230M@rs");
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
        int i = 0;
        for (JSONObject jo : objectList) {
            shopriteInsert(jo, i++);
        }
    }

    public boolean selectAllShopriteItems() {

        BeanList instance = BeanList.getInstance();
        List<ShopriteBean> list = new ArrayList<>();

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(SELECT_SHOPRITE_ITEMS);

            while(rs.next()) {
                ShopriteBean bean = new ShopriteBean();
                bean.setCode(rs.getString("code"));
                bean.setName(rs.getString("name"));
                bean.setPrice(rs.getString("price"));
                bean.setTimestamp(rs.getTimestamp("created_on"));
                list.add(bean);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        instance.setBean(list);
        return true;
    }

    public void shopriteInsert(JSONObject jo, int i) {

        try {
            PreparedStatement pstmt = conn.prepareStatement(SHOPRITE_INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, jo.getString("id"));
            pstmt.setString(2, jo.getString("name"));
            pstmt.setString(3, jo.getString("price"));
            pstmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));

            pstmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
