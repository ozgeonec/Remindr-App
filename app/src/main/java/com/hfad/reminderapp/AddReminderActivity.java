package com.hfad.reminderapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {

    private TextView remindMe;
    private TextView clockDisplay;
    private TextView dateDisplay;
    private String remindText;
    private ImageButton calendarButton;
    private ImageButton clockButton;
    //private ImageButton bellButton;
    //private ImageButton repeatButton;
    //private ImageButton addButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timePickerListener;
    //String amPm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);

        //Initialize Views
        remindMe = (EditText)findViewById(R.id.remind_me);
        calendarButton = (ImageButton)findViewById(R.id.calendarbutton);
        clockButton = (ImageButton)findViewById(R.id.clockbutton);
        //bellButton = (ImageButton)findViewById(R.id.bellbutton);
        //repeatButton = (ImageButton)findViewById(R.id.repeatbutton);
        //addButton = (ImageButton)findViewById(R.id.addbutton);
        dateDisplay = (TextView)findViewById(R.id.dateDisplay);
        clockDisplay = (TextView)findViewById(R.id.clocktext);

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
        //date setting
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
                month = month + 1;
                Log.d("AddReminderActivity", "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                dateDisplay.setText(date);
            }
        };
        //time setting
       clockButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar cal = Calendar.getInstance();
               int hour = cal.get(Calendar.HOUR);
               int minute=cal.get(Calendar.MINUTE);
               TimePickerDialog dialog2 = new TimePickerDialog(AddReminderActivity.this,
                       android.R.style.Theme_Material_Dialog_MinWidth,timePickerListener,minute,hour,false);
               dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
               dialog2.show();
           }
       });
       timePickerListener = new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
              /* if (hourOfDay >= 12) {
                   amPm = "PM";
               } else {
                   amPm = "AM";
               }*/
               clockDisplay.setText(hourOfDay + ":" + minute);
           }
       };













    }


}
