package com.example.TickerOrder.dbcode;

public class Date {
    private int year;
    private int month;
    private int day;

    public Date(String str) {
        String[] tmp = str.split("-|/");
        year = Integer.parseInt(tmp[0]);
        month = Integer.parseInt(tmp[1]);
        day = Integer.parseInt(tmp[2]);
    }

    public Date(int month, int day){

    }

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(Date date) {
        this(date.year, date.month, date.day);
    }

    public boolean later(Date date) {
        if(date.year > this.year)
            return true;
        else if(date.year == this.year) {
            if(date.month > this.month)
                return true;
            else if(date.month == this.month)
                if(date.day > this.day)
                    return true;
                else
                    return false;
        }
        return false;
    }

    public boolean equals(Date date) {
        if(date.year != this.year)
            return false;
        else if(date.month != this.month)
            return false;
        else if(date.day != this.day)
            return false;
        return true;
    }

    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }

    public String transfrom() {
        String str2 = String.format("%02d", day);
        return (month + "_" + str2);
    }

    public String toString() {
        String str1 = String.format("%02d", month);
        String str2 = String.format("%02d", day);
        return (year + "-" + str1 + "-" + str2);
    }
}
