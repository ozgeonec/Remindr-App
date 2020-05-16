package com.hfad.reminderapp;

/**
 * @author ozgeonec
 */
public class Reminder {
    private final int id;
    private final String title;
    private final String date;
    private final String time;
    private final String repeat;
    private final String repeatNmbr;
    private final String repeatType;
    private final String tagType;

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

    public String getRepeatNmbr() {
        return repeatNmbr;
    }

    public int getId() {
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

    public String getTagType() {
        return tagType;
    }
}
