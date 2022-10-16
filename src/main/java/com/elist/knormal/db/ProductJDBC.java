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
    private static final String SR_INSERT = "INSERT INTO shoprite_items(code, name, price, created_on) values (?,?,?,?)";
    private static final String SR_UPDATE = "UPDATE shoprite_items set name = ?, price = ? where code = ?";
    private static final String SELECT_SR_ITEMS = "Select * from shoprite_items";
    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/grocerylistdb";

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
        List<ShopriteBean> list = new ArrayList<>();

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(SELECT_SR_ITEMS);

            while (rs.next()) {
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
