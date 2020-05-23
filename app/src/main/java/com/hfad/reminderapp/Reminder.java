package com.hfad.reminderapp;

/**
 * @author ozgeonec
 */
public class Reminder {
    private int id;
    private  String title;
    private  String date;
    private  String time;
    private  String repeat;
    private  String repeatNmbr;
    private  String repeatType;
    private  String tagType;

    public Reminder(int id, String title, String date, String time, String repeat, String repeatNmbr, String repeatType, String tagType) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.repeat = repeat;
        this.repeatNmbr = repeatNmbr;
        this.repeatType = repeatType;
        this.tagType = tagType;
    }

    public Reminder() {

    }

    public Reminder(String remindText, String mDate, String mTime, String mRepeat, String mRepeatNmbr, String mRepeatType, String mTag) {
        this.title = remindText;
        this.date = mDate;
        this.time = mTime;
        this.repeat = mRepeat;
        this.repeatNmbr = mRepeatNmbr;
        this.repeatType = mRepeatType;
        this.tagType = mTag;
    }

    public String getRepeatNmbr() {
        return repeatNmbr;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getRepeat() {
        return repeat;
    }

    public String getTime() {
        return time;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public String getTagType() { return tagType; }

    public void setID(int id){
        this.id=id;
    }
    public void setTitle(String title){ this.title=title;}
    public void setDate(String date){
        this.date=date;
    }
    public void setTime(String time){
        this.time=time;
    }
    public void setRepeat(String repeat){
        this.repeat=repeat;
    }
    public void setRepeatType(String repeatType){
        this.repeatType=repeatType;
    }
    public void setRepeatNmbr(String repeatNmbr){
        this.repeatNmbr=repeatNmbr;
    }
    public void setTagType(String tagType){
        this.tagType=tagType;
    }

    public static String toString(Reminder reminder) {
        String text = "";
        text +=
                "Title: " + reminder.getTitle() + "\n"+
                "Date: " + reminder.getDate() + "\n" +
                "Time: " + reminder.getTime() + "\n" +
                "Tag: " + reminder.getTagType() + "\n";
        return text;
    }


}
