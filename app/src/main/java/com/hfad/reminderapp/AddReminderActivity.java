package com.hfad.reminderapp;
/**
 * @author ozgeonec
 */

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.Objects;



public class AddReminderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText remindMe;
    private TextView clockDisplay;
    private TextView dateDisplay;
    private String remindText;
    private TextView mRepeatTypeText;
    private TextView mRepeatText;
    private TextView mRepeatNoText;
    private ImageButton calendarButton;
    private ImageButton clockButton;
    private ImageButton repeatButton;
    private ImageButton addButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timePickerListener;
    private Spinner tagChoice;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String mTime;
    private String mDate;
    private String mRepeat;
    private String mRepeatNmbr;
    private String mRepeatType;
    private String mTag;
    //private String numberText;
    private long mRepeatTime;
    private Switch repeatSwitch;
    private TextView everyText;
    private Calendar mCalendar;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;
    private static final long milYear = 155520000000L;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);

        //Initialize Views

        remindMe = (EditText)findViewById(R.id.remind_me);
        calendarButton = (ImageButton)findViewById(R.id.calendarbutton);
        clockButton = (ImageButton)findViewById(R.id.clockbutton);
        repeatButton = (ImageButton)findViewById(R.id.repeatbutton);
        addButton = (ImageButton)findViewById(R.id.addbutton);
        dateDisplay = (TextView)findViewById(R.id.dateDisplay);
        clockDisplay = (TextView)findViewById(R.id.clocktext);
        tagChoice = (Spinner)findViewById(R.id.tagchoices);

        repeatSwitch = (Switch)findViewById(R.id.repswitch);
        mRepeatTypeText = (TextView)findViewById(R.id.repeatType);
        mRepeatNoText = (TextView)findViewById(R.id.repeatNoText);
        mRepeatText = (TextView) findViewById(R.id.repeatText);
        everyText = (TextView)findViewById(R.id.everyText);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

      

        //Auto-settings
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        mRepeat = "false";
        mRepeatNmbr = Integer.toString(1);
        mRepeatType = "Hour";


        // Setup TextViews using reminder values
        dateDisplay.setText(mDate);
        clockDisplay.setText(mTime);
        mRepeatNoText.setText("");
        mRepeatTypeText.setText("");
        everyText.setText("Every");
        everyText.setVisibility(View.GONE);
        mRepeatText.setText("Off");


        //Setup Reminder Title
        remindMe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                remindText = s.toString().trim();
                remindMe.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Date Setting
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddReminderActivity.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                mDay = day;
                mMonth = month + 1;
                mYear = year;
                mDate = mDay + "/" + mMonth + "/" + mYear;

                dateDisplay.setText(mDate);
            }
        };
        //Time Setting
       clockButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar cal = Calendar.getInstance();
               int hour = cal.get(Calendar.HOUR);
               int minute = cal.get(Calendar.MINUTE);
               TimePickerDialog dialog2 = new TimePickerDialog(AddReminderActivity.this,
                       android.R.style.Theme_Material_Dialog_MinWidth,timePickerListener,minute,hour,false);
               Objects.requireNonNull(dialog2.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.RED));
               dialog2.show();
           }
       });
       timePickerListener = new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

               mHour = hourOfDay;
               mMinute = minute;
               if(hourOfDay < 10){
                   mTime = "0" + hourOfDay + ":" + minute;
               }else{
                   mTime = hourOfDay + ":" + minute;
               }
               if (minute < 10) {
                   mTime = hourOfDay + ":" + "0" + minute;
               } else {
                   mTime = hourOfDay + ":" + minute;
               }
               clockDisplay.setText(mTime);

           }
       };

       //Tag choice
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tag_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tagChoice.setAdapter(adapter);
        

        //Alarm Setting
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemindrDatabase rb = new RemindrDatabase(AddReminderActivity.this);

                mCalendar.set(Calendar.DAY_OF_MONTH,mDay);
                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);
                mTag = tagChoice.getSelectedItem().toString();


                // Check repeat type
                if (mRepeatType.equals("Minute(s)")) {
                    mRepeatTime = Integer.parseInt(mRepeatNmbr) * milMinute;
                } else if (mRepeatType.equals("Hour(s)")) {
                    mRepeatTime = Integer.parseInt(mRepeatNmbr) * milHour;
                } else if (mRepeatType.equals("Day(s)")) {
                    mRepeatTime = Integer.parseInt(mRepeatNmbr) * milDay;
                } else if (mRepeatType.equals("Week(s)")) {
                    mRepeatTime = Integer.parseInt(mRepeatNmbr) * milWeek;
                } else if (mRepeatType.equals("Month(s)")) {
                    mRepeatTime = Integer.parseInt(mRepeatNmbr) * milMonth;
                } else if(mRepeatType.equals("Year(s)")){
                    mRepeatTime = Integer.parseInt(mRepeatNmbr) * milYear;
                }
                // Creating Reminder
                int ID = rb.addReminder(new Reminder(remindText, mDate, mTime, mRepeat, mRepeatNmbr, mRepeatType, mTag));
                if(repeatSwitch.isChecked()){
                    mRepeat = "true";
                    new AlarmReceiver().setRepeatAlarm(getApplicationContext(), mCalendar, ID, mRepeatTime);
                    Toast.makeText(getApplicationContext(),"Saved with Repeat",Toast.LENGTH_SHORT).show();

                }else{
                    new AlarmReceiver().setAlarm(getApplicationContext(),mCalendar, ID);
                    Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();

                }
                Intent intent = new Intent(v.getContext(), ListReminderActivity.class);
                startActivity(intent);
            onBackPressed();
            }
        });
    }
    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    // Creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // On clicking the back arrow

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // On clicking the repeat switch
    public void onSwitchRepeat(View view) {
        if (repeatSwitch.isChecked()) {
            mRepeat="true";
            mRepeatTypeText.setText("Hours");
            mRepeatTypeText.setVisibility(View.VISIBLE);
            mRepeatNoText.setText("1");
            mRepeatNoText.setVisibility(View.VISIBLE);
            everyText.setVisibility(View.VISIBLE);
            mRepeatText.setVisibility(View.GONE);
        } else {
            mRepeatText.setText(R.string.repeat_off);
            mRepeatText.setVisibility(View.VISIBLE);
            mRepeatTypeText.setVisibility(View.GONE);
            mRepeatNoText.setVisibility(View.GONE);
            everyText.setVisibility(View.GONE);
        }
    }
    //setting repeat type
    public void selectRepeatType(View v){
        final String[] items = new String[6];

        items[0] = "Minute(s)";
        items[1] = "Hour(s)";
        items[2] = "Day(s)";
        items[3] = "Week(s)";
        items[4] = "Month(s)";
        items[5] = "Year(s)";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Type");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mRepeatType = items[item];
                mRepeatTypeText.setText(mRepeatType);
                //mRepeatText.setText("Every " + mRepeatNoText + " " + mRepeatTypeText + "(s)");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    // On clicking repeat interval button
    public void setRepeatNo(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Number");

        // Create EditText box to input repeat number
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (input.getText().toString().length() == 0) {
                            mRepeatNmbr = Integer.toString(1);
                            mRepeatNoText.setText(mRepeatNmbr);
                            mRepeatText.setText("Every " + mRepeatNoText+ " " + mRepeatTypeText );
                        }
                        else {
                            mRepeatNmbr = input.getText().toString().trim();
                            mRepeatNoText.setText(mRepeatNmbr);
                            mRepeatText.setText("Every " + mRepeatNoText + " " + mRepeatTypeText);
                        }
                    }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

}
