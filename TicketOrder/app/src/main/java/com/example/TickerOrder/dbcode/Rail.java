package com.example.TickerOrder.dbcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Rail implements Comparable{

    private int tag;
    //	private int hour[] = new int[12];
    //	private int minute[] = new int[12];
    private Time depTime;
    private Time arrived_time;
    private Date date;
    private int ticket_num;
    private boolean early = false;
    private String start_station;
    private String end_station;
    private int seatNum;
    private int state;
    private boolean university = false;
    public String sdepTime;
    public String sarrTime;
    public String IDCard;

    private int[][] ordinary = {
            {0, 260, 310, 500, 700, 920, 1330, 1510, 1660, 1880, 2290, 2500},
            {40, 0, 260, 440, 640, 850, 1250, 1430, 1600, 1820, 2230, 2440},
            {70, 40,  0, 400, 590, 800,	1210, 1390, 1550, 1780,	2180, 2390},
            {200, 160, 130, 0, 400, 620, 1010, 1210, 1370, 1580, 1990, 2200},
            {330, 290, 260, 130, 0, 410, 820, 1010, 1160, 1390, 1790, 2000},
            {480, 430, 400, 280, 140, 0, 610, 790, 950, 1160, 1580, 1790},
            {750, 700, 670, 540, 410, 270, 0, 400, 550, 770, 1180, 1390},
            {870, 820, 790, 670, 540, 390, 130, 0, 370, 580, 1000, 1210},
            {970, 930, 900, 780, 640, 500, 230, 110, 0, 430, 830, 1040},
            {1120, 1080, 1050, 920, 790, 640, 380, 250, 150, 0, 620, 820},
            {1390, 1350, 1320, 1190, 1060, 920, 650, 530, 420, 280, 0, 410},
            {1530, 1490, 1460, 1330, 1200, 1060, 790, 670, 560, 410, 140, 0}
    };

    enum Station{南港, 台北, 板橋, 桃園, 新竹, 苗栗, 台中, 彰化, 雲林, 嘉義, 台南, 左營};

    public int getTag() {
        return tag;
    }

    public Date getDate() {
        return new Date(date);
    }

    public Time getStart() {
        return new Time(depTime);
    }

    public Time getEnd() {
        return new Time(arrived_time);
    }

    public String getSStart(){
        return sdepTime;
    }

    public String getSEnd(){
        return sarrTime;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setEarly(boolean early) {
        this.early = early;
    }

    public void setTicket(int ticket_num) {
        this.ticket_num = ticket_num;
    }

    public int getTicket() {
        return ticket_num;
    }

    public Rail(String tag, String date, String depTime, String arrived_time,
                String start_station, String end_station, String seatNum, String state, String university, String IDCard) {
        this.sdepTime = depTime;
        this.sarrTime = arrived_time;

        this.tag = Integer.parseInt(tag);
        this.date = new Date(date);
        this.depTime = new Time(depTime);
        this.arrived_time = new Time(arrived_time);
        this.start_station = start_station;
        this.end_station = end_station;
        this.seatNum = Integer.parseInt(seatNum);
        this.state = Integer.parseInt(state);
        this.IDCard = IDCard;
        if(Integer.parseInt(university)==1){ this.university = true;}
    }

    public Rail(int tag, Date date, Time depTime, Time arrived_time ) {
        this.tag = tag;
        this.date = date;
        this.depTime = depTime;
        this.arrived_time = arrived_time;
    }

    public Rail(int tag, Date date, Time depTime, int num, int state, String s, String e) {

        this.tag = tag;
        this.date = date;
        this.depTime = depTime;
        this.start_station = s;
        this.end_station = e;
        this.state = state;
        this.ticket_num = num;
    }

    public Rail(int tag, String date, String depTime, int num, int state, String s, String e) {
        this.sdepTime = depTime;
        this.tag = tag;
        this.date = new Date(date);
        this.depTime = new Time(depTime);
        this.start_station = s;
        this.end_station = e;
        this.state = state;
        this.ticket_num = num;
    }

    public Rail(int tag, String date, String depTime) {
        this.tag = tag;
        this.date = new Date(date);
        this.depTime = new Time(depTime);
    }


    private int change(int price, double discount) {
        price = (int)Math.floor(price * discount);
        price = price - price % 5;
        return price;
    }

    private String show(int total, boolean enough, boolean university) {
        if(university) {
            if(ticket_num + total <= 25) {
                return "5折";
            }
            else if(ticket_num + total <= 75) {
                return "75折";
            }
            else if(ticket_num + total <= 175) {
                return "88折";
            }
            else {
                return "";
            }
        }
        else {
            if(early) {
                if(ticket_num + total <= 25) {
                    return "65折";
                }
                else if(ticket_num + total <= 75) {
                    return "8折";
                }
                else if(ticket_num + total <= 175) {
                    return "9折";
                }
                else {
                    return "";
                }
            }
            else {
                return "";
            }
        }
    }
    // state = 0，代表沒有限制
    // state = 1，代表靠窗
    // state = 2，代表靠走道
    // state = 3，商務艙沒有限制
    // state = 4，代表商務艙靠窗
    // state = 5，代表商務艙靠走道
    // 此函數會將可以訂票的車次跟座位的組合，例如8 5A，變成一個String
    // 若是團體票會盡量將座位靠在一起
    // 蒐集所有可能的String，變為陣列
    public String[] NoSeat() {
        if(seatNum > 1) {
            if(state == 1 || state == 2) {
                state = 0;
            }
            else if(state == 4 || state == 5) {
                state = 3;
            }
        }
        boolean check = true;
        boolean condition = true;
        String[] tmp = new String[668];
        String[] store = new String[668];
        int sum = 0;
        int spare = 0;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "password");
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        Station start = Station.valueOf(this.start_station);
        Station end = Station.valueOf(this.end_station);
        int begin = start.ordinal();
        int finish = end.ordinal();

        if(begin > finish) {
            begin = begin + finish;
            finish = begin - finish;
            begin = begin - finish;
            begin++;
            finish++;
        }
        PreparedStatement ps = null;
        try {
            if(state < 3) {
                String sql = String.format("select `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, car_num, seat from `%s` where car_id = %d and car_num <= 9 and car_num != 6"
                        , this.date.transfrom(), tag);
                ps = conn.prepareStatement(sql);
            }
            else {
                String sql = String.format("select `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `11`, `12`, car_num, seat from `%s` where car_id = %d and car_num = 6"
                        , this.date.transfrom(), tag);
                ps = conn.prepareStatement(sql);
            }
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        try {
            while(rs.next()){
                for(int i = begin; i < finish; i++) {
                    if(rs.getInt(Integer.toString(i+1)) == 1) {
                        check = false;
                    }
                }
                if(state % 3 == 1) {
                    String x = rs.getString("seat");
                    if(x.charAt(x.length()-1) != 'A' && x.charAt(x.length()-1) != 'E') {
                        condition = false;
                    }
                }
                else if(state % 3 == 2) {
                    String x = rs.getString("seat");
                    if(x.charAt(x.length()-1) != 'C' && x.charAt(x.length()-1) != 'D') {
                        condition = false;
                    }
                }
                if(check) {
                    if(condition)	tmp[sum++] = Integer.toString(rs.getInt("car_num")) + " " + rs.getString("seat");
                    else	store[spare++] = Integer.toString(rs.getInt("car_num")) + " " + rs.getString("seat");
                }
                check = true;
                condition = true;
            }
        } catch (SQLException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        if((sum+spare) >= seatNum) {
            if(sum != 0) {
                String[] str = new String[sum];
                for(int i = 0; i < sum; i++) {
                    str[i] = tmp[i];
                }
                return str;
            }
            else {
                String[] str = new String[spare];
                for(int i = 0; i < spare; i++) {
                    str[i] = store[i];
                }
                return str;
            }
        }
        else {
            return null;
        }
    }

    // num代表團體票人數
    // 將可能的String矩陣傳入，進行隨機選擇
    // 會印出隨機的座位
    public Ticket[] BookSeat(String[] str) {
        int n;
        int price = 0;
        int fixed_price = 0;
        Station start = Station.valueOf(this.start_station);
        Station end = Station.valueOf(this.end_station);
        int begin = start.ordinal();
        int finish = end.ordinal();
        Ticket[] ticket = new Ticket[seatNum];
        if(begin > finish) {
            begin = begin + finish;
            finish = begin - finish;
            begin = begin - finish;
            if(str[0].charAt(0) == '6') {
                fixed_price = ordinary[begin][finish];
            }
            else {
                fixed_price = ordinary[finish][begin];
            }
            begin++;
            finish++;
        }
        else {
            if(str[0].charAt(0) == '6') {
                fixed_price = ordinary[begin][finish];
            }
            else {
                fixed_price = ordinary[finish][begin];
            }
        }
        if(seatNum == 1) {
            n = (int)(Math.random() * str.length);
            if(str[0].charAt(0) != '6') {
                switch(show(1, early, university)) {
                    case "5折":
                        price = change(fixed_price, 0.5);
                        ticket[0] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[n], "5折", IDCard);
                        break;
                    case "65折":
                        price = change(fixed_price, 0.65);
                        ticket[0] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[n], "65折", IDCard);
                        break;
                    case "75折":
                        price = change(fixed_price, 0.75);
                        ticket[0] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[n], "75折", IDCard);
                        break;
                    case "8折":
                        price = change(fixed_price, 0.8);
                        ticket[0] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[n], "8折", IDCard);
                        break;
                    case "88折":
                        price = change(fixed_price, 0.88);
                        ticket[0] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[n], "88折", IDCard);
                        break;
                    case "9折":
                        price = change(fixed_price, 0.9);
                        ticket[0] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[n], "9折", IDCard);
                        break;
                    default:
                        ticket[0] = new Ticket(tag, date, depTime, arrived_time, fixed_price, start_station, end_station, str[n], "", IDCard);
                        break;
                }
            }
            else {
                ticket[0] = new Ticket(tag, date, depTime, arrived_time, fixed_price, start_station, end_station, str[n], "", IDCard);
            }
            return ticket;
        }
        else {
            if(str[0].charAt(0) == '6') {
                int select;
                select = (int)(Math.random()*(str.length-seatNum+1));
                for(int i = 0; i < seatNum; i++) {
                    ticket[i] = new Ticket(tag, date, depTime, arrived_time, fixed_price, start_station, end_station, str[select+i], "", IDCard);
                }
                return ticket;
            }
            else {
                char[] p = {'1', '2', '4', '8', '3', '9', '5', '7'};
                int[] q = {0, 0, 0, 0, 0, 0, 0, 0};
                int[] random = new int[8];
                int i = 0, choose = 0, sum = 0, select;
                boolean available = false;
                for(int index = 0; index < 8; index++) {
                    while(str[i].charAt(0) == p[index] && i < str.length-1) {
                        q[index]++;
                        i++;
                    }
                    if(i == str.length-1) {
                        if(str[i].charAt(0) == p[index])	q[index]++;
                    }
                }
                for(i = 0; i < 8; i++) {
                    if(q[i] >= seatNum) {
                        random[choose] = i;
                        choose++;
                        available = true;
                    }
                }
                if(available) {
                    n = (int)(Math.random() * choose);
                    for(i = 0; i < random[n]; i++)	{
                        sum += q[i];
                    }
                    select = (int)(Math.random()*(q[random[n]]-seatNum+1));
                    for(i = 0; i < seatNum; i++) {
                        switch(show(i+1, early, university)) {
                            case "5折":
                                price = change(fixed_price, 0.5);
                                ticket[i] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+i+sum], "5折", IDCard);
                                break;
                            case "65折":
                                price = change(fixed_price, 0.65);
                                ticket[i] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+i+sum], "65折", IDCard);
                                break;
                            case "75折":
                                price = change(fixed_price, 0.75);
                                ticket[i] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+i+sum], "75折", IDCard);
                                break;
                            case "8折":
                                price = change(fixed_price, 0.8);
                                ticket[i] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+i+sum], "8折", IDCard);
                                break;
                            case "88折":
                                price = change(fixed_price, 0.88);
                                ticket[i] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+i+sum], "88折", IDCard);
                                break;
                            case "9折":
                                price = change(fixed_price, 0.9);
                                ticket[i] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+i+sum], "9折", IDCard);
                                break;
                            default:
                                price = change(fixed_price, 1);
                                ticket[i] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+i+sum], null, IDCard);
                                break;
                        }
                    }
                    return ticket;
                }
                else	{
                    select = (int)(Math.random()*(str.length-seatNum+1));
                    for(int j = 0; j < seatNum; j++) {
                        switch(show(j+1, early, university)) {
                            case "5折":
                                price = change(fixed_price, 0.5);
                                ticket[j] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+j], "5折", IDCard);
                                break;
                            case "65折":
                                price = change(fixed_price, 0.65);
                                ticket[j] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+j], "65折", IDCard);
                                break;
                            case "75折":
                                price = change(fixed_price, 0.75);
                                ticket[j] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+j], "75折", IDCard);
                                break;
                            case "8折":
                                price = change(fixed_price, 0.8);
                                ticket[j] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+j], "8折", IDCard);
                                break;
                            case "88折":
                                price = change(fixed_price, 0.88);
                                ticket[j] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+j], "88折", IDCard);
                                break;
                            case "9折":
                                price = change(fixed_price, 0.9);
                                ticket[j] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+j], "9折", IDCard);
                                break;
                            default:
                                price = change(fixed_price, 1);
                                ticket[j] = new Ticket(tag, date, depTime, arrived_time, price, start_station, end_station, str[select+j], null, IDCard);
                                break;
                        }
                    }
                    return ticket;
                }
            }
        }
    }

    public boolean equals(Rail a) {
        if(a.tag != this.tag)
            return false;
        else if(!a.date.equals(this.date))
            return false;
        else if(!a.depTime.equals(this.depTime))
            return false;
        else if(!a.arrived_time.equals(this.arrived_time))
            return false;
        return true;
    }

    @Override
    public int compareTo(Object o) {
        Rail others = (Rail)o;
        if(this.date.later(others.date))
            return -1;
        else if(this.date.equals(others.date)) {
            if(this.depTime.later(others.depTime))
                return -1;
            else if(this.depTime.equals(others.depTime))
                return 0;
            else
                return 1;
        }
        return 1;
    }

    public String toString() {
        return (tag + " " + date.toString() + " " + depTime.toString()
                //+ " " + arrived_time.toString()
        );
    }
}
