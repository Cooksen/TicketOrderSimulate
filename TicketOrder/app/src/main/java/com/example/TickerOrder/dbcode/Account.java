package com.example.TickerOrder.dbcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Account {

    private String accountTag;
    private String IDCard;
    private String password;
    private boolean logInState;

    public Account() {
    }

    public String getID() {
        return IDCard;
    }


    public static int CreateAccount (String accountTag, String IDCard, String phone, String password) {

        //1 verify account tag
        //1.1 check is all chinese
        for(int i=0; i<accountTag.length(); i++) {
            String aWord = accountTag.substring(i, i+1);
            if(!aWord.matches("[\\u4E00-\\u9FA5]+")) {
                return 1;
            }
        }

        //1.2 check length < 2 || > 5
        if(accountTag.length()<2 || accountTag.length()>5) {
            return 1;
        }

        //2 verify IDCard -> Based on ?啁頨怠?霅?霅???
        String[] code = {"A","B","C","D","E","F","G","H","I","J","K","M","N","O","P","Q","T","U","V","W","X","Z"};
        String[] num = {"10","11","12","13","14","15","16","17","34","18","19","21","22","35","23","24","27","28","29","32","30","33"};
        String IDCardCode = IDCard.substring(0,1);
        String IDCardNum = "";
        for(int i=0; i<code.length; i++) {
            if(IDCardCode.equals(code[i]))
                IDCardNum = num[i];
        }
        //2.1 If code given is not correct
        if(IDCardNum == "")
            return 2;
        else {
            IDCardNum = IDCardNum + IDCard.substring(1, 10);
        }
        //2.2 If number is not match with logic
        int[] logicNum = {1,9,8,7,6,5,4,3,2,1,1};
        int sum = 0;
        for(int i =0; i<logicNum.length; i++) {
            char n = IDCardNum.charAt(i);
            int aNum = Character.getNumericValue(n);
            sum += (aNum * (logicNum[i]));
        }
        if(sum%10 != 0) {
            return 2;
        }

        //3 verify phone number
        //3.1 check length = 10
        if(phone.length()!= 10) {
            return 3;
        }
        //3.2 check start with 09
        String frontPhone = phone.substring(0, 2);
        if(!frontPhone.equals("09")){
            return 3;
        }
        //4 verify password
        //4.1 check length >= 6 (Keep safe)
        if(password.length()<6) {
            return 4;
        }

        //5 account already exist
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
            Statement stmt = conn.createStatement();


        //5.1 check no same phone number
        String phonesql = "select phone from ACCOUNT;";
        PreparedStatement ps = null;
        ResultSet phoneResult = null;

            ps = conn.prepareStatement(phonesql);
            phoneResult = ps.executeQuery();
            while(phoneResult.next()){
                if(phoneResult.getString(1).equals(phone))
                    return 5;
            }


        //5.2 check no same IDCard
        String IDsql = "select IDCard from ACCOUNT;";
        PreparedStatement IDps = null;
        ResultSet IDResult = null;

            IDps = conn.prepareStatement(IDsql);
            IDResult = IDps.executeQuery();
            while(IDResult.next()){
                if(IDResult.getString(1).equals(IDCard))
                    return 5;
            }

        //6 everything is correct
            String sql1=String.format("INSERT INTO ACCOUNT VALUES ('%s', '%s', '%s', '%s')", accountTag, IDCard, phone, password);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 6;
    }

    public static String login(String  IDCard, String password) {
        //check is there match account
        boolean matchIDCard = false;
        boolean matchPassword = false;
        String name="";
        String phone="";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
            Statement stmt = conn.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //1 match IDCard
        String IDsql = "select IDCard from ACCOUNT;";
        PreparedStatement IDps = null;
        ResultSet IDResult = null;
        try {
            IDps = conn.prepareStatement(IDsql);
            IDResult = IDps.executeQuery();
            while(IDResult.next()){
                if(IDResult.getString(1).equals(IDCard))
                    matchIDCard = true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //2 match password
        try {
            conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
            Statement stmt = conn.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //1 match IDCard
        String Passwordsql = "select password from ACCOUNT;";
        PreparedStatement Passwordps = null;
        ResultSet PasswordResult = null;
        try {
            Passwordps = conn.prepareStatement(Passwordsql);
            PasswordResult = Passwordps.executeQuery();
            while(PasswordResult.next()){
                if(PasswordResult.getString(1).equals(password))
                    matchPassword = true;
            }
            if (matchPassword==true){
                String sql = "SELECT name FROM ACCOUNT WHERE Password = ?";
                PreparedStatement st = conn.prepareStatement(sql);
                st.setString(1,password);
                ResultSet rs = st.executeQuery();
                name = rs.toString();

                String sql1 = "SELECT phone FROM ACCOUNT WHERE Password = ?";
                PreparedStatement st1 = conn.prepareStatement(sql);
                st.setString(1,password);
                ResultSet rs1 = st.executeQuery();
                phone = rs1.toString();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(matchIDCard && matchPassword) {
            return "1"+" "+ name + " " + phone +" " + IDCard;
        }
        else if(matchIDCard) {
            return "2";
        }
        return "3";
    }

    public boolean in() {
        return logInState;
    }

//    public static void main(String[] args) {
//        Account a = new Account();
//        int message = a.CreateAccount("廖玟棋", "N226220766","0937778906" , "mypassword");
//        System.out.println(message);//return 6
//
//        System.out.println(a.login("E197646101","123")); //return false;
//
//    }

}