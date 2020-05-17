package com.hfad.reminderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ozgeonec
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ReminderItem> mItems;
    private RemindrDatabase rb;

    public MyAdapter() {
        mItems = new ArrayList<>();
    }
    public void setItemCount(int count) {
        mItems.clear();
        mItems.addAll(generateData(count));
        notifyDataSetChanged();
    }
    public void onDeleteItem(int count) {
        mItems.clear();
        mItems.addAll(generateData(count));
    }
    public void removeItemSelected(int selected) {
        if (mItems.isEmpty()) return;
        mItems.remove(selected);
        notifyItemRemoved(selected);
    }

   @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReminderItem item = mItems.get(position);
        /*holder.setReminderTitle(item.mTitle);
        holder.setReminderDateTime(item.mDateTime);
        holder.setReminderRepeatInfo(item.mRepeat, item.mRepeatNo, item.mRepeatType);
        holder.setActiveImage(item.mTag);*/
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    // Generate real data for each item
    public List<ReminderItem> generateData(int count) {
        ArrayList<ReminderItem> items = new ArrayList<>();

        // Get all reminders from the database
        List<Reminder> reminders = rb.getAllReminders();

        // Initialize lists
        List<String> Titles = new ArrayList<>();
        List<String> Repeats = new ArrayList<>();
        List<String> RepeatNos = new ArrayList<>();
        List<String> RepeatTypes = new ArrayList<>();
        List<String> Tags = new ArrayList<>();
        List<String> DateAndTime = new ArrayList<>();
        List<Integer> IDList = new ArrayList<>();
        //List<DateTimeSorter> DateTimeSortList = new ArrayList<>();

        // Add details of all reminders in their respective lists
        for (Reminder r : reminders) {
            Titles.add(r.getTitle());
            DateAndTime.add(r.getDate() + " " + r.getTime());
            Repeats.add(r.getRepeat());
            RepeatNos.add(r.getRepeatNmbr());
            RepeatTypes.add(r.getRepeatType());
            Tags.add(r.getTagType());
            IDList.add(r.getID());
        }
        return items;
    }
}
