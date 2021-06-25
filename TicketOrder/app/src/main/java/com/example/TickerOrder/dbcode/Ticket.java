package com.example.TickerOrder.dbcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ticket extends Rail {

    private String seat;
    private String from;
    private String to;
    private int price;
    private String discount;
    private String IDCard;
    private Date date;

    enum Station{南港, 台北, 板橋, 桃園, 新竹, 苗栗, 台中, 彰化, 雲林, 嘉義, 台南, 左營};

    public Ticket(int tag, Date date, Time depTime, Time arrived_time, int price, String from, String to, String seat, String discount, String IDCard) {
        super(tag, date, depTime, arrived_time);
        this.seat = seat;
        this.price = price;
        this.from = from;
        this.to = to;
        this.discount = discount;
        this.IDCard = IDCard;
        this.date = date;
    }

    public String dateStr() {
        String res = date.toString();
        String tmp[] = res.split("-");
        return tmp[0]+"/"+tmp[1]+"/"+tmp[2];
    }

    public String getId() {return IDCard;}

    public String getSeat() {
        return seat;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    private void discount() {
        switch(discount) {
            case "5折":
                discount = "大學生5折";
                break;
            case "65折":
                discount = "早鳥65折";
                break;
            case "75折":
                discount = "大學生75折";
                break;
            case "8折":
                discount = "早鳥8折";
                break;
            case "88折":
                discount = "大學生88折";
                break;
            case "9折":
                discount = "早鳥9折";
                break;
            default:
                discount = "成人";
                break;
        }
    }

    private void seat() {
        String[] p = seat.split(" ");
        if(p[0] == "6") {
            seat = ("商務廂 " + p[0] + " 座位" +  " " + p[1]);
        }
        else {
            seat = ("標準廂 " + p[0] + " 座位" +  " " + p[1]);
        }
    }

    public void update() {
        Station start = Station.valueOf(from);
        Station end = Station.valueOf(to);
        int begin = start.ordinal();
        int finish = end.ordinal();
        if(begin > finish) {
            begin = begin + finish;
            finish = begin - finish;
            begin = begin - finish;
            begin++;
            finish++;
        }
        for(int i = begin; i < finish; i++) {
            String[] p = seat.split(" ");
            String sql = String.format("UPDATE `%s` SET `%d` = 1 where `seat` = '%s' and `car_num` = %d and `car_id` = %d"
                    , this.getDate().transfrom(), i+1, p[1],  Integer.parseInt(p[0]), getTag());
            try (
                    Connection conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
                    Statement stmt = conn.createStatement()){

                stmt.executeUpdate(sql);
            } catch (SQLException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }
        String sql1 = String.format("select Discount from car_time where `car_id` = %d and `date` = '%s'", getTag(), getDate().toString());
        PreparedStatement ps = null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
            ps = conn.prepareStatement(sql1);
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
                setTicket(rs.getInt("Discount"));
            }
        }
        catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String[] p = seat.split(" ");
        String sql2 = String.format("UPDATE car_time SET `Discount` = %d where `car_id` = %d and `date` = '%s'"
                , getTicket()+1, getTag(), getDate().toString());
        String sql3=String.format("INSERT INTO record VALUES ('%s', %d, %d, %d, '%s', '%s', '%s', %d, '%s',0)"
                , IDCard, getTag(), begin, finish, getDate().toString(), getStart().toString(), getEnd().toString(), Integer.parseInt(p[0]), p[1]);
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
                Statement stmt = conn.createStatement()){

            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

    public void updatePrice(String seat,int price){
        String tmp[] = seat.split(" ");
        String sql = String.format("UPDATE record SET price = %d where `seat` = '%s' and `IDCard` = '%s' and `date` = '%s'"
                , price, tmp[1], IDCard, getDate().toString());
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
                Statement stmt = conn.createStatement()){

            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

    public void DeleteRec(){
        String[] p = seat.split(" ");
        Station start = Station.valueOf(from);
        Station end = Station.valueOf(to);
        int begin = start.ordinal();
        int finish = end.ordinal();
        String sql1;
        sql1 = String.format("DELETE from record where seat = ? and car_num = %d and car_id = %d and DepTime = ? and AriTime = ? and date = ? and start = %d and end = %d ;\n"
                , Integer.parseInt(p[0]), getTag(), begin+1, finish+1);

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
            PreparedStatement st1 = conn.prepareStatement(sql1);
            Object[] param = {getSeat(), getStart().toString(),getEnd().toString(), getDate().toString()};
            st1.setString(1,p[1]);
            st1.setString(2,getStart().toString());
            st1.setString(3,getEnd().toString());
            st1.setString(4,getDate().toString());

            st1.executeUpdate();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void DeleteSeat() {
        String[] p = seat.split(" ");
        Station start = Station.valueOf(from);
        Station end = Station.valueOf(to);
        int begin = start.ordinal();
        int finish = end.ordinal();
        if(begin > finish) {
            begin = begin + finish;
            finish = begin - finish;
            begin = begin - finish;
            begin++;
            finish++;
        }
        String sql;
        String sql1;
        try {
            for(int i = begin; i <= finish; i++) {

            sql = String.format("UPDATE %s SET `%d` = 0 where car_num = %d and car_id = %d and seat = ? ; \n"
                    ,getDate().transfrom() , i+1,  Integer.parseInt(p[0]), getTag());
                System.out.println(String.format("UPDATE ? SET `%d` = 0 where car_num = %d and car_id = %d and seat = ? ; \n"
                        , i+1,  Integer.parseInt(p[0]), getTag())+getDate().transfrom()+p[1]);

            sql1 = String.format("DELETE from record where seat = ? and car_num = %d and car_id = %d and DepTime = ? and AriTime = ? and date = ? and start = %d and end = %d ;\n"
                        , Integer.parseInt(p[0]), getTag(), begin+1, finish+1);

                System.out.println(String.format("delete from record where seat = ? and car_num = %d and car_id = %d and DepTime = ? and AriTime = ? and date = ? and start = %d and end = %d"
                        , Integer.parseInt(p[0]), getTag(), begin+1, finish+1)+" "
                +p[1]+" "+getStart().toString()+" "+getEnd().toString()+" "+getDate().toString());

                Connection conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
                Statement stmt = conn.createStatement();

                PreparedStatement st = conn.prepareStatement(sql);
                PreparedStatement st1 = conn.prepareStatement(sql1);
                st.setString(1,p[1]);
                st1.setString(1,p[1]);
                st1.setString(2,getStart().toString());
                st1.setString(3,getEnd().toString());
                st1.setString(4,getDate().toString());
                st.executeUpdate();
                st1.executeUpdate();
                }

        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

    public String toString() {
        discount();
        seat();
        return String.format("%s   車次train %d 單程票%n%s %s   %s %s%n%s %n%s %nNT$ "
                        + "%d 現金",
                getDate().toString(), getTag(), from, getStart().toString(), to, getEnd().toString(), discount, seat, price);
    }
}