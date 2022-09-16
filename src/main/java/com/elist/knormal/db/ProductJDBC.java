package com.elist.knormal.db;

import org.json.JSONObject;

import java.sql.*;
import java.util.List;

public class ProductJDBC {
    private Connection conn;
    private static ProductJDBC instance;
    private static final String SHOPRITE_INSERT = "INSERT INTO grocerylistdb(code, name, price, created_on) values (?,?,?,?)";

    private ProductJDBC() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/grocerylistdb", "postgres","password");
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

        }
    }

    public void shopriteInsert(JSONObject jo) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(SHOPRITE_INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, jo.get("code").toString());
            pstmt.setString(2, jo.get("name").toString());
            pstmt.setString(3, jo.get("price").toString());
            pstmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {

            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
