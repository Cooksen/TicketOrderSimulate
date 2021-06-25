package com.example.TickerOrder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class getRecord {

    public static ArrayList<String> getRecords(String idInput) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        PreparedStatement ps = null;
        String sql = String.format("select * from `record` where IDCard = '%s' ", idInput);

        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ResultSet rs = null;

        try {
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        ArrayList<String> ar = new ArrayList<String>();

        try {
            String temp = "";
            while(rs.next()) {
                temp += rs.getString(1) + " ";
                temp += rs.getString(2) + " ";
                temp += rs.getString(3) + " ";
                temp += rs.getString(4) + " ";
                temp += rs.getString(5) + " ";
                temp += rs.getString(6) + " ";
                temp += rs.getString(7) + " ";
                temp += rs.getString(8) + " ";
                temp += rs.getString(9) + " ";
                temp += rs.getString(10)+ " ";
                ar.add(temp);
//                多加一個price
                temp = "";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ar;
    }
}
