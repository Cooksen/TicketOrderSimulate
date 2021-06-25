package com.example.TickerOrder.dbcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Record {
enum Station{南港, 台北, 板橋, 桃園, 新竹, 苗栗, 台中, 彰化, 雲林, 嘉義, 台南, 左營};
//enum Station{左營, 台南, 嘉義, 雲林, 彰化, 台中, 苗栗, 新竹, 桃園, 板橋, 台北, 南港};
public String startsta;
public String endsta;
private Rail[] total = new Rail[196];
public String result="";
public int num = 0;
public int state = 0;


public Record(int num, int state, String s, String e, int year, int month, int day, int hour, int minute){
        Time tmp1 = new Time(hour, minute);
        Date test = new Date(year, month, day);
        this.startsta = s;
        this.endsta = e;
        this.num = num;
        this.state = state;

        SetRail();
        }
// 將資料庫的列車蒐集起來
// 以時間順序進行排序
private void SetRail() {
        int i = 0;
        Connection conn = null;
        try {
        conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
        } catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        }

        String sql = "select car_id,date,depTime,arrived_time, Discount from car_time";
        PreparedStatement ps = null;
        try {
        ps = conn.prepareStatement(sql);
        } catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        }

        ResultSet rs = null;
        try {
        rs = ps.executeQuery();
        } catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        }
        try {
        while(rs.next()){
        int id  = rs.getInt("car_id");
        String date = rs.getString("date");
        String depTime = rs.getString("depTime");
        String arrived_time = rs.getString("arrived_time");
        total[i++] = new Rail(id, date, depTime, num, state, startsta, endsta);
        }
        }
        catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        }
        Arrays.sort(total, 0, i);
        }

private int FindIndex(Rail a) {
        for(int i = 0; i < 196; i++) {
        if(a.compareTo(total[i]) == -1)
        return i;
        }
        return -1;
        }
//
// 印出之後五筆可能的列車
public Rail[] Search(int year, int month, int day, int hour, int minute, int now_year, int now_month, int now_day, int now_hour, int now_minute, String s, String e, int num, int state, boolean university) {
        Station start = Station.valueOf(s);
        Station end = Station.valueOf(e);
        int begin = start.ordinal();
        int finish = end.ordinal();
        Time tmp1 = new Time(hour, minute);
        Time present = new Time(now_hour, now_minute);
        Date test = new Date(year, month, day);
        Date today = new Date(now_year, now_month, now_day+5);
        Rail p = new Rail(100, test, tmp1
                , num, state, s, e
        );
        Rail now = new Rail(100, today, present
                , num, state, s, e
        );
        Rail[] tmp = new Rail[5];
        int i = FindIndex(p);
        int k = 0;

        this.startsta = s;
        this.endsta = e;

        while(i < 196 && k < 5) {
        if(CheckAvailable(total[i].getTag(), s, e) && total[i].NoSeat(
                //num,state,s,e
        ) != null) {
        if(state < 3) {
        String sql = String.format("select Discount from car_time where `car_id` = %d and `date` = '%s'",
                total[i].getTag(), total[i].getDate().toString());
        PreparedStatement ps = null;
        try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");;
        ps = conn .prepareStatement(sql);
        } catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        }

        ResultSet rs = null;
        try {
        rs = ps.executeQuery();
        } catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        }
        try {
        while(rs.next()){
        total[i].setTicket(rs.getInt("Discount"));
        }
        }
        catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
        }
        if(finish > begin && total[i].getTag() % 2 == 0) {
        tmp[k++] = total[i];
        if(total[i].compareTo(now) != -1 && !university) {
        total[i].setEarly(true);
        if((total[i].getTicket() + num) <= 25) {
        result += String.format(total[i].toString() + " " + "65折"+"/");
//								System.out.println(total[i].toString() + " " + "65折");
        }
        else if((total[i].getTicket() + num) <= 75) {
        result += String.format(total[i].toString() + " " + "8折"+"/");
//								System.out.println(total[i].toString() + " " + "8折");
        }
        else if((total[i].getTicket() + num) <= 175) {
        result += String.format(total[i].toString() + " " + "9折"+"/");
//								System.out.println(total[i].toString() + " " + "9折");
        }
        else {
        result += String.format(total[i].toString()+ " " + "一般票"+"/");
//								System.out.println(total[i].toString());
        }
        }
        else if(university){
        if((total[i].getTicket() + num) <= 25) {
        result += String.format(total[i].toString() + " " + "5折"+"/");
//								System.out.println(total[i].toString() + " " + "5折");
        }
        else if((total[i].getTicket() + num) <= 75) {
        result += String.format(total[i].toString() + " " + "75折"+"/");
//								System.out.println(total[i].toString() + " " + "75折");
        }
        else if((total[i].getTicket() + num) <= 175) {
        result += String.format(total[i].toString() + " " + "88折"+"/");
//								System.out.println(total[i].toString() + " " + "88折");
        }
        else {
        result += String.format(total[i].toString()+ " " + "一般票"+"/");
//								System.out.println(total[i].toString());
        }
        }
        else {
        result += String.format(total[i].toString()+ " " + "一般票"+"/");
//							System.out.println(total[i].toString());
        }
        }
        else if(finish < begin && total[i].getTag() % 2 == 1){
        tmp[k++] = total[i];
        if(total[i].compareTo(now) != -1 && !university) {
        total[i].setEarly(true);
        if((total[i].getTicket() + num) <= 25) {
        result += String.format(total[i].toString() + " " + "65折"+"/");
//								System.out.println(total[i].toString() + " " + "65折");
        }
        else if((total[i].getTicket() + num) <= 75) {
        result += String.format(total[i].toString() + " " + "8折"+"/");
//								System.out.println(total[i].toString() + " " + "8折");
        }
        else if((total[i].getTicket() + num) <= 175) {
        result += String.format(total[i].toString() + " " + "9折"+"/");
//								System.out.println(total[i].toString() + " " + "9折");
        }
        else {
        result += String.format(total[i].toString()+ " " + "一般票"+"/");
//								System.out.println(total[i].toString());
        }
        }
        else if(university){
        if((total[i].getTicket() + num) <= 25) {
        result += String.format(total[i].toString() + " " + "5折"+"/");
//								System.out.println(total[i].toString() + "(" + "5折" + ")");
        }
        else if((total[i].getTicket() + num) <= 75) {
        result += String.format(total[i].toString() + " " + "75折"+"/");
//								System.out.println(total[i].toString() + "(" + "75折" + ")");
        }
        else if((total[i].getTicket() + num) <= 175) {
        result += String.format(total[i].toString() + " " + "88折"+"/");
//								System.out.println(total[i].toString() + " " + "88折");
        }
        else {
        result += String.format(total[i].toString()+ " " + "一般票"+"/");
//								System.out.println(total[i].toString());
        }
        }
        else {
        result += String.format(total[i].toString()+ " " + "一般票"+"/");
//							System.out.println(total[i].toString());
        }
        }
        }
        else {
        if(finish > begin && total[i].getTag() % 2 == 0) {
        tmp[k++] = total[i];
        result += String.format(total[i].toString()+ " " + "一般票" +"/");
//						System.out.println(total[i].toString());
        }
        else if(finish < begin && total[i].getTag() % 2 == 1) {
        tmp[k++] = total[i];
        result += String.format(total[i].toString()+ " " + "一般票"+"/");
//						System.out.println(total[i].toString());
        }
        }
        }
        i++;
        }
        if(k == 0) return null;
        else return tmp;
        }

private static boolean CheckAvailable(int tag, String s, String e) {
        boolean[] stay = new boolean[12];
        int tmp = tag / 100;
        if(tmp == 1) {
        for(int i = 0; i < 12; i++) {
        stay[i] = true;
        }
        }
        else if(tmp == 5) {
        for(int i = 1; i < 7; i++) {
        stay[i] = true;
        }
        }
        else if(tmp == 10) {
        for(int i = 0; i < 3; i++) {
        stay[i] = true;
        }
        stay[6] = true;
        stay[11] = true;
        }
        Station start = Station.valueOf(s);
        Station end = Station.valueOf(e);
        int begin = start.ordinal();
        int finish = end.ordinal();
        if(stay[begin] == true && stay[finish] == true) {
        return true;
        }
        return false;
        }
public String getInfo() {
        return result;
        }

}

