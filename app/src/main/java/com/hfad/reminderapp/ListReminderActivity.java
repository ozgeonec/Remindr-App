package com.hfad.reminderapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ozgeonec
 *
 */
public class ListReminderActivity extends AppCompatActivity {

    private FloatingActionButton addReminderButton;
    private RemindrDatabase rb;
    private Toolbar toolbar;
    private RecyclerView mList;
    private SimpleAdapter mAdapter;
    private TextView mNoReminderView;
    private LinkedHashMap<Integer, Integer> IDmap = new LinkedHashMap<>();
    private MultiSelector mMultiSelector = new MultiSelector();
    private AlarmReceiver mAlarmReceiver;
    private TextView tagDisplay;
    private TextDrawable mDrawableBuilder;
    private ImageView mThumbnailImage;
    private int newReminderCount=0;
    private int oldReminderCount=0;
    private TextView reminderCountView;
    private TextView oldReminderCountView;
    private String newReminderCountText;
    private String oldReminderCountText;

    //SharedPreferences preferences;
    //final String darkModeKey = "darkModeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpage);

        mNoReminderView = (TextView)findViewById(R.id.no_reminder_text);
        mList = (RecyclerView)findViewById(R.id.reminderList);
        addReminderButton = (FloatingActionButton)findViewById(R.id.bigAddButton);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tagDisplay = (TextView)findViewById(R.id.tagdisplay);
        mThumbnailImage = (ImageView) findViewById(R.id.delete_image);
        reminderCountView = (TextView)findViewById(R.id.activeReminders);
        oldReminderCountView = (TextView)findViewById(R.id.finishedReminders);


        newReminderCountText = Integer.toString(newReminderCount);
        oldReminderCountText = Integer.toString(oldReminderCount);


        //Toolbar settings
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        // Initialize reminder database
        rb = new RemindrDatabase(getApplicationContext());

        // If there are no reminders display a message asking the user to create reminders
        List<Reminder> test = rb.getAllReminders();

        if (test.isEmpty()) {
            mNoReminderView.setVisibility(View.VISIBLE);
        }

        // Create recycler view
        mList.setLayoutManager(getLayoutManager());
        registerForContextMenu(mList);
        SimpleAdapter adapter = new SimpleAdapter();
        adapter.setItemCount(100);
        mList.setAdapter(adapter);

        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddReminderActivity.class);
                startActivity(intent);
            }
        });
        mAlarmReceiver = new AlarmReceiver();
    }

    // Recreate recycler view
    @Override
    public void onResume(){
        super.onResume();

        // To check is there are saved reminders
        // If there are no reminders display a message asking the user to create reminders
        List<Reminder> mTest = rb.getAllReminders();

        if (mTest.isEmpty()) {
            mNoReminderView.setVisibility(View.VISIBLE);
        } else {
            mNoReminderView.setVisibility(View.GONE);
        }

        //mAdapter.setItemCount(100);
    }

    // Layout manager for recycler view
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    /*protected int getDefaultItemCount() {
        return 100;
    }*/
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }*/

    public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.VerticalItemHolder> {

        private int mTempPost;
        private ArrayList<SimpleAdapter.ReminderItem> mItems;

        public SimpleAdapter() {
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
            if(newReminderCount!=0){
                newReminderCount--;
                newReminderCountText = Integer.toString(newReminderCount);
                reminderCountView.setText(newReminderCountText);
            }else{
                newReminderCount--;
                newReminderCountText = Integer.toString(newReminderCount);
                reminderCountView.setText(newReminderCountText);
            }
        }

        public void removeItemSelected(int selected) {
            if (mItems.isEmpty()) return;
            mItems.remove(selected);
            notifyItemRemoved(selected);

        }

        // View holder for recycler view items
        @Override
        public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View root = inflater.inflate(R.layout.recycler_items, container, false);

            return new VerticalItemHolder(root, this);

        }

        @Override
        public void onBindViewHolder(VerticalItemHolder itemHolder, int position) {

            SimpleAdapter.ReminderItem item = mItems.get(position);
            itemHolder.setReminderTitle(item.mTitle);
            itemHolder.setReminderDateTime(item.mDateTime);
            itemHolder.setReminderRepeatInfo(item.mRepeat, item.mRepeatNo, item.mRepeatType);
            itemHolder.setTagType(item.mTagType);
            if(newReminderCount<=20){
                newReminderCount++;
                newReminderCountText = Integer.toString(newReminderCount);
                reminderCountView.setText(newReminderCountText);
                //oldReminderCountView.setText(oldReminderCountText);
            }else{
                newReminderCount=1;
                newReminderCountText = Integer.toString(newReminderCount);
                reminderCountView.setText(newReminderCountText);
                //oldReminderCountView.setText(oldReminderCountText);
            }

        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        // Class for recycler view items
        public  class ReminderItem {
            public String mTitle;
            public String mDateTime;
            public String mRepeat;
            public String mRepeatNo;
            public String mRepeatType;
            public String mTagType;

            public ReminderItem(String Title, String DateTime, String Repeat, String RepeatNo, String RepeatType, String TagType) {
                this.mTitle = Title;
                this.mDateTime = DateTime;
                this.mRepeat = Repeat;
                this.mRepeatNo = RepeatNo;
                this.mRepeatType = RepeatType;
                this.mTagType = TagType;
            }
        }

        // Classes to compare date and time
        public class DateAndTimeComparator implements Comparator {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm",Locale.getDefault());

            public int compare(Object a, Object b) {
                String o1 = ((DateTimeSorter)a).getDateTime();
                String o2 = ((DateTimeSorter)b).getDateTime();

                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        // UI and data class for recycler view items
        public  class VerticalItemHolder extends SwappingHolder
                implements View.OnClickListener {
            private TextView mTitleText, timeDisplay, mRepeatInfoText, tagDisplay;
            private ImageView mActiveImage , mThumbnailImage;
            private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
            private TextDrawable mDrawableBuilder;
            private SimpleAdapter mAdapter;
            //private ImageButton deleteButton;
            private TextView dateDisplay;
            private Button menuButton2;


            public VerticalItemHolder(final View itemView, final SimpleAdapter adapter) {
                super(itemView, mMultiSelector);
                itemView.setOnClickListener(this);
                //itemView.setLongClickable(true);

                // Initialize adapter for the items
                mAdapter = adapter;

                // Initialize views
                mTitleText = (TextView) itemView.findViewById(R.id.recycle_title);
                timeDisplay = (TextView) itemView.findViewById(R.id.timedisplay);
                dateDisplay = (TextView)itemView.findViewById(R.id.date_display);
                mRepeatInfoText = (TextView) itemView.findViewById(R.id.repeat_display);
                //mActiveImage = (ImageButton) itemView.findViewById(R.id.bell_image);
                mThumbnailImage = (ImageView) itemView.findViewById(R.id.delete_image);
                tagDisplay = (TextView)itemView.findViewById(R.id.tagdisplay);
                menuButton2 = (Button)itemView.findViewById(R.id.menuButton);
                menuButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(getApplicationContext(),menuButton2);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.discard_reminder:
                                        mMultiSelector.setSelected(getAdapterPosition(),0,true);
                                        // Get the reminder id associated with the recycler view item
                                        for (int i = IDmap.size(); i >= 0; i--) {
                                            if (mMultiSelector.isSelected(i,0)) {
                                                int id = IDmap.get(i);
                                                // Get reminder from reminder database using id
                                                Reminder temp = rb.getReminder(id);
                                                // Delete reminder
                                                rb.deleteReminder(temp);
                                                // Remove reminder from recycler view
                                                mAdapter.removeItemSelected(i);
                                                // Delete reminder alarm
                                                mAlarmReceiver.cancelAlarm(getApplicationContext(), id);
                                                if(newReminderCount!=0){
                                                    newReminderCount--;
                                                    newReminderCountText = Integer.toString(newReminderCount);
                                                    reminderCountView.setText(newReminderCountText);
                                                }else{
                                                    newReminderCount--;
                                                    newReminderCountText = Integer.toString(newReminderCount);
                                                    reminderCountView.setText(newReminderCountText);
                                                }
                                            }
                                        }
                                        // Clear selected items in recycler view
                                        mMultiSelector.clearSelections();
                                        // Recreate the recycler items
                                        // This is done to remap the item and reminder ids
                                        mAdapter.onDeleteItem(100);

                                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                        // To check is there are saved reminders
                                        // If there are no reminders display a message asking the user to create reminders
                                        List<Reminder> mTest = rb.getAllReminders();

                                        if (mTest.isEmpty()) {
                                            mNoReminderView.setVisibility(View.VISIBLE);
                                        } else {
                                            mNoReminderView.setVisibility(View.GONE);
                                        }
                                        return true;
                                    case R.id.edit_reminder:
                                        int mReminderClickID = IDmap.get(mTempPost);
                                        selectReminder(mReminderClickID);

                                        return true;
                                    case R.id.share:
                                        mMultiSelector.setSelected(getAdapterPosition(),0,true);
                                        for (int i = IDmap.size(); i >= 0; i--) {
                                            if (mMultiSelector.isSelected(i,0)) {
                                                int id = IDmap.get(i);
                                                // Get reminder from reminder database using id
                                                Reminder temp = rb.getReminder(id);
                                                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                                                emailIntent.setType("plain/text");
                                                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Reminder.toString(temp));
                                                startActivity(Intent.createChooser(emailIntent,"Sent"));
                                            }
                                        }
                                        return true;
                                    case R.id.mark:
                                        mMultiSelector.setSelected(getAdapterPosition(),0,true);
                                        for (int i = IDmap.size(); i >= 0; i--) {
                                            if (mMultiSelector.isSelected(i,0)) {
                                                int id = IDmap.get(i);
                                                // Get reminder from reminder database using id
                                                Reminder temp = rb.getReminder(id);
                                                // Delete reminder
                                                rb.deleteReminder(temp);
                                                // Remove reminder from recycler view
                                                mAdapter.removeItemSelected(i);
                                                // Delete reminder alarm
                                                mAlarmReceiver.cancelAlarm(getApplicationContext(), id);
                                            }
                                        }
                                        mMultiSelector.clearSelections();
                                        // Recreate the recycler items
                                        // This is done to remap the item and reminder ids
                                        if(newReminderCount!=0){
                                            newReminderCount--;
                                            newReminderCountText = Integer.toString(newReminderCount);
                                            reminderCountView.setText(newReminderCountText);
                                        }else{
                                            newReminderCount--;
                                            newReminderCountText = Integer.toString(newReminderCount);
                                            reminderCountView.setText(newReminderCountText);
                                        }
                                        if(oldReminderCount<=20){
                                            oldReminderCount++;
                                            oldReminderCountText = Integer.toString(oldReminderCount);
                                            oldReminderCountView.setText(oldReminderCountText);
                                        }else{
                                            oldReminderCount=1;
                                            oldReminderCountText = Integer.toString(oldReminderCount);
                                            oldReminderCountView.setText(oldReminderCountText);
                                        }

                                        mAdapter.onDeleteItem(100);
                                        Toast.makeText(getApplicationContext(), "Marked Finished", Toast.LENGTH_SHORT).show();
                                        return true;

                                    default: break;
                                }
                                return false;
                            }
                        });
                        popupMenu.inflate(R.menu.list_menu);
                        popupMenu.setGravity(Gravity.RIGHT);
                        try {
                            Field mFieldPopup=popupMenu.getClass().getDeclaredField("mPopup");
                            mFieldPopup.setAccessible(true);
                            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popupMenu);
                            mPopup.setForceShowIcon(true);
                        } catch (Exception e) { }
                        popupMenu.show();
                    }
                });
            }
            // On clicking a reminder item
            private void selectReminder(int mClickID) {
                String mStringClickID = Integer.toString(mClickID);
               Intent i = new Intent(ListReminderActivity.this, EditReminderActivity.class);
                i.putExtra(EditReminderActivity.EXTRA_REMINDER_ID, mStringClickID);
                startActivityForResult(i, 1);
            }

           @Override
            public void onClick(View v) {
               mTempPost = mList.getChildAdapterPosition(v);
               int mReminderClickID = IDmap.get(mTempPost);
               selectReminder(mReminderClickID);

            }

            // Set reminder title view
            public void setReminderTitle(String title) {
                mTitleText.setText(title);
            }

            // Set date and time views
            public void setReminderDateTime(String datetime) {
                dateDisplay.setText(datetime);
            }
            // Set repeat views
            public void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
                if(repeat.equals("true")){
                    mRepeatInfoText.setText("Every " + repeatNo + " " + repeatType);
                }else if (repeat.equals("false")) {
                    mRepeatInfoText.setText("Repeat Off");
                }
            }
            //Set tag type
            public void setTagType(String tagType){
                tagDisplay.setText(tagType);
                int color;
                if (tagType.equals("Birthday")) {
                    color = Color.YELLOW;
                    mDrawableBuilder = TextDrawable.builder()
                            .buildRound(String.valueOf(mTitleText).substring(0,1),color);
                    mThumbnailImage.setImageDrawable(mDrawableBuilder);
                }
                else if(tagType.equals("Work")){
                    color = Color.CYAN;
                    mDrawableBuilder = TextDrawable.builder()
                            .buildRound(String.valueOf(mTitleText).substring(0,1),color);
                    mThumbnailImage.setImageDrawable(mDrawableBuilder);
                }
                else if(tagType.equals("General")){
                    color = Color.GREEN;
                    mDrawableBuilder = TextDrawable.builder()
                            .buildRound(String.valueOf(mTitleText).substring(0,1),color);
                    mThumbnailImage.setImageDrawable(mDrawableBuilder);
                }
                else if(tagType.equals("Personal")){
                    color = Color.MAGENTA;
                    mDrawableBuilder = TextDrawable.builder()
                            .buildRound(String.valueOf(mTitleText).substring(0,1),color);
                    mThumbnailImage.setImageDrawable(mDrawableBuilder);
                }
                else if(tagType.equals("Health")){
                    color = Color.BLUE;
                    mDrawableBuilder = TextDrawable.builder()
                            .buildRound(String.valueOf(mTitleText).substring(0,1),color);
                    mThumbnailImage.setImageDrawable(mDrawableBuilder);
                }
            }
        }

        // Generate random test data
        public SimpleAdapter.ReminderItem generateDummyData() {
            return new SimpleAdapter.ReminderItem("1", "2", "3", "4", "5", "6");
        }

        // Generate real data for each item
        public List<SimpleAdapter.ReminderItem> generateData(int count) {
            ArrayList<SimpleAdapter.ReminderItem> items = new ArrayList<>();

            // Get all reminders from the database
            List<Reminder> reminders = rb.getAllReminders();

            // Initialize lists
            List<String> Titles = new ArrayList<>();
            List<String> DateAndTime = new ArrayList<>();
            List<String> Repeats = new ArrayList<>();
            List<String> RepeatNos = new ArrayList<>();
            List<String> RepeatTypes = new ArrayList<>();
            List<String> Tags = new ArrayList<>();


            List<Integer> IDList= new ArrayList<>();
            List<DateTimeSorter> DateTimeSortList = new ArrayList<>();

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

            int key = 0;

            // Add date and time as DateTimeSorter objects
            for(int k = 0; k<Titles.size(); k++){
                DateTimeSortList.add(new DateTimeSorter(key, DateAndTime.get(k)));
                key++;
            }

            // Sort items according to date and time in ascending order
            Collections.sort(DateTimeSortList, new SimpleAdapter.DateAndTimeComparator());


            int k = 0;

            // Add data to each recycler view item
            for (DateTimeSorter item:DateTimeSortList) {
                int i = item.getIndex();

                items.add(new SimpleAdapter.ReminderItem(Titles.get(i), DateAndTime.get(i), Repeats.get(i),
                        RepeatNos.get(i), RepeatTypes.get(i), Tags.get(i)));
                IDmap.put(k, IDList.get(i));


                k++;
            }
            return items;
        }
    }
}
