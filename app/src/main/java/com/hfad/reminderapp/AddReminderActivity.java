package com.hfad.reminderapp;
/**
 * @author ozgeonec
 */

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
import java.util.Objects;

public class AddReminderActivity extends AppCompatActivity {

    private EditText remindMe;
    private TextView clockDisplay;
    private TextView dateDisplay;
    private String remindText;
    private String numberText;
    private ImageButton calendarButton;
    private ImageButton clockButton;
    //private ImageButton repeatButton;
    //private ImageButton addButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timePickerListener;
    //String amPm;
    private Spinner tagChoice;
    private Spinner repeatChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreminder);

        //Initialize Views
        remindMe = (EditText)findViewById(R.id.remind_me);
        calendarButton = (ImageButton)findViewById(R.id.calendarbutton);
        clockButton = (ImageButton)findViewById(R.id.clockbutton);
        //repeatButton = (ImageButton)findViewById(R.id.repeatbutton);
        //addButton = (ImageButton)findViewById(R.id.addbutton);
        dateDisplay = (TextView)findViewById(R.id.dateDisplay);
        clockDisplay = (TextView)findViewById(R.id.clocktext);
        tagChoice = (Spinner)findViewById(R.id.tagchoices);
        repeatChoice = (Spinner)findViewById(R.id.repeatchoice);

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
                month = month + 1;
                Log.d("AddReminderActivity", "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                dateDisplay.setText(date);
            }
        };
        //Time Setting
       clockButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar cal = Calendar.getInstance();
               int hour = cal.get(Calendar.HOUR);
               int minute=cal.get(Calendar.MINUTE);
               TimePickerDialog dialog2 = new TimePickerDialog(AddReminderActivity.this,
                       android.R.style.Theme_Material_Dialog_MinWidth,timePickerListener,minute,hour,false);
               Objects.requireNonNull(dialog2.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.RED));
               dialog2.show();
           }
       });
       timePickerListener = new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
               clockDisplay.setText(hourOfDay + ":" + minute);
           }
       };
       //Tag choice
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tag_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tagChoice.setAdapter(adapter);
        addListenerOnSpinnerItemSelection();
        addListenerOnButton();

        //Repeat Choice
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.repeatchoices, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatChoice.setAdapter(adapter2);
    }



    //Spinner functions
    public void addListenerOnSpinnerItemSelection(){

        tagChoice.setOnItemSelectedListener(new SpinnerActivity());
        repeatChoice.setOnItemSelectedListener(new SpinnerActivity());
    }
    public void addListenerOnButton(){
        tagChoice = (Spinner)findViewById(R.id.tagchoices);
        repeatChoice = (Spinner)findViewById(R.id.repeatchoice);


        Toast.makeText(AddReminderActivity.this,
                "On Button Click : " +
                        "\n" + String.valueOf(tagChoice.getSelectedItem()) ,
                Toast.LENGTH_LONG).show();
    }
}
