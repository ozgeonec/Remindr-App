package com.hfad.reminderapp;



/**
 * @author ozgeonec
 */
public class ReminderItem {
    public String mTitle;
    public String mDateTime;
    public String mRepeat;
    public String mRepeatNo;
    public String mRepeatType;
    public String mTag;

    public ReminderItem(String Title, String DateTime, String Repeat, String RepeatNo, String RepeatType, String Tag) {
        this.mTitle = Title;
        this.mDateTime = DateTime;
        this.mRepeat = Repeat;
        this.mRepeatNo = RepeatNo;
        this.mRepeatType = RepeatType;
        this.mTag = Tag;
    }


}
