package com.example.TickerOrder.dbcode;

public class Time {
    private int hour;
    private int minute;

    public Time(String str) {
        String[] tmp = str.split(":");
        hour = Integer.parseInt(tmp[0]);
        minute = Integer.parseInt(tmp[1]);
    }

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Time(Time time) {
        this(time.hour, time.minute);
    }

    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    public boolean later(Time t) {
        if(t.getHour() > this.getHour()) {
            return true;
        }
        else if(t.getHour() == this.getHour()) {
            if(t.getMinute() > this.getMinute()) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Time t) {
        return (this.minute == t.minute && this.hour == t.hour);
    }
    public String toString() {
        String str1 = String.format("%02d", hour);
        String str2 = String.format("%02d", minute);
        return (str1 + ":" + str2);
    }
}
