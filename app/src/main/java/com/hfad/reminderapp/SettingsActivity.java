package com.hfad.reminderapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ozgeonec
 */
public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SharedPreferences preferences;
    String itemRingTone = "";
    Uri itemRingToneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    int itemRingTonePos = 0;

    private Spinner ringSpinner;
    private CheckBox vibration;
    private Button saveButton;
    private Button backButton;
    private ConstraintLayout conslay;

    final static String vibrationKey = "vibrationKey";
    final static String ringKey = "ringKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        preferences = getSharedPreferences("SQL", 0);

        ringSpinner = (Spinner) findViewById(R.id.ringtone_spinner);
        vibration = (CheckBox) findViewById(R.id.vibrcheckBox);
        saveButton = (Button)findViewById(R.id.settingsSave_button);
        conslay = (ConstraintLayout)findViewById(R.id.settings_layout);
        backButton = (Button)findViewById(R.id.goback);
        //ringSpinner.setOnItemSelectedListener(SettingsActivity.this);

        List<String> ringtone = new ArrayList<>();
        ringtone.add("ALARM");
        ringtone.add("NOTIFICATION");
        ringtone.add("RINGTONE");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ringtone);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ringSpinner.setAdapter(stringArrayAdapter);

        if(preferences.getLong(vibrationKey,0) != 0) {
            vibration.setChecked(true);
        }
        else {
            vibration.setChecked(false);
        }
        String tone = preferences.getString(ringKey, "ALARM");
        if(tone.equalsIgnoreCase("ALARM")){
            ringSpinner.setSelection(0);
        } else if (tone.equalsIgnoreCase("NOTIFICATION")){
            ringSpinner.setSelection(1);
        } else if (tone.equalsIgnoreCase("RINGTONE")){
            ringSpinner.setSelection(2);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                long vib = vibration.isChecked() ? 12345678 : 0;
                editor.putString(ringKey, itemRingToneUri.toString());
                editor.putLong(vibrationKey, vib);
                editor.commit();
                Toast.makeText(SettingsActivity.this, "Saved", Toast.LENGTH_LONG).show();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        itemRingTone = parent.getItemAtPosition(position).toString();
        itemRingTonePos = position;
        if(position == 0){
            itemRingToneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        } else if (position == 1){
            itemRingToneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        } else if (position == 2){
            itemRingToneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            //Empty on-purpose
    }
}
