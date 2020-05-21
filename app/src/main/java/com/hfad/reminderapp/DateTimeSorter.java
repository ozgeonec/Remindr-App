package com.hfad.reminderapp;

/**
 * @author ozgeonec
 */
public class DateTimeSorter {
    public int mIndex;
    public String mDate;
    public String mTime;



    public DateTimeSorter(int index, String Date, String Time){
        mIndex = index;
        mDate = Date;
        mTime = Time;
    }

    public DateTimeSorter(){}


    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public String getDate() {
        return mDate;
    }

   // public void setDate(String date) { mDate = date; }
    public void setmTime(String time) {
        mTime = time;
    }
    public void setmDate(String date){mDate = date;}

    public String getTime(){
        return mTime;
    }


}
