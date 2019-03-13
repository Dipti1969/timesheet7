/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.report;

public class Time {

    protected int hour;
    protected int minute;
    protected int second;
    protected String hour1;
    protected String minute1;
    protected String second1;

    public Time() {
    }

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public void setDoubleDigit() {
        this.second1 = getDoubleDigit(second);
        this.hour1 = getDoubleDigit(hour);
        this.minute1 = getDoubleDigit(minute);

    }

    public Time addTime(Time t1, Time t2) {
        Time sum = new Time();
        sum.hour = t1.hour + t2.hour;
        sum.minute = t1.minute + t2.minute;
        sum.second = t1.second + t2.second;

        if (sum.second >= 60.0) {
            sum.second -= 60.0;
            sum.minute += 1;
        }
        if (sum.minute >= 60) {
            sum.minute -= 60;
            sum.hour += 1;
        }
        return sum;
    }
        public Time addTime1(Time t1, Time t2) {
        Time sum = new Time();
        sum.hour = t1.hour + t2.hour;
        sum.minute = t1.minute + t2.minute;
        sum.second = t1.second + t2.second;

        if (sum.second >= 100.0) {
            sum.second -= 100.0;
            sum.minute += 1;
        }
       if (sum.minute >= 100) {
            sum.minute -= 100;
            sum.hour += 1;
        }
        return sum;
    }
    
    public String getDoubleDigit(int i) {
        String newI = null;
        switch (i) {
            case 0:
                newI = "00";
                break;
            case 1:
                newI = "01";
                break;
            case 2:
                newI = "02";
                break;
            case 3:
                newI = "03";
                break;
            case 4:
                newI = "04";
                break;
            case 5:
                newI = "05";
                break;
            case 6:
                newI = "06";
                break;
            case 7:
                newI = "07";
                break;
            case 8:
                newI = "08";
                break;
            case 9:
                newI = "09";
                break;
            default:                
                newI = Integer.toString(i);                
        }
        return newI;
    }
}
