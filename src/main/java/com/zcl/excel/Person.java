package com.zcl.excel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhaocl1 on 2018/6/10.
 */
public class Person {
    private String no;
    private String name;
    private String dapartment;
    private Date alldate;
    private String sdate;
    private String stime;
    private int day;
    private int hour;

    public int getHour() {
        if (this.alldate != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(alldate);
            return cal.get(Calendar.HOUR_OF_DAY);
        }
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (this.alldate != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(alldate);
            this.day = cal.get(Calendar.DAY_OF_MONTH);
        }
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDapartment() {
        return dapartment;
    }

    public void setDapartment(String dapartment) {
        this.dapartment = dapartment;
    }

    public Date getAlldate() {
        return alldate;
    }

    public void setAlldate(Date alldate) {
        this.alldate = alldate;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        if (this.alldate != null){
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            this.sdate = fmt.format(this.alldate);
        }else {
            this.sdate = sdate;
        }
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        if (this.alldate != null){
            SimpleDateFormat fmt2 = new SimpleDateFormat("HH:mm:ss");
            this.stime = fmt2.format(this.alldate);
        }else {
            this.stime = stime;
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        System.out.println("day="+cal.get(Calendar.DAY_OF_MONTH));
    }
}
