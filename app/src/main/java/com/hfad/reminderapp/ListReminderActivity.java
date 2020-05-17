package com.hfad.reminderapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ozgeonec
 */
public class ListReminderActivity extends AppCompatActivity {

    private TextView noReminder;
    private RecyclerView recycleList;
    private FloatingActionButton addReminderButton;
    private RemindrDatabase rb;
    private AlarmReceiver alarmReceiver;
    private SimpleAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpage);

        noReminder = (TextView)findViewById(R.id.no_reminder_text);
        recycleList = (RecyclerView)findViewById(R.id.reminderList);
        addReminderButton = (FloatingActionButton)findViewById(R.id.bigAddButton);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Initialize reminder database
        rb = new RemindrDatabase(getApplicationContext());


        // If there are no reminders display a message asking the user to create reminders
        List<Reminder> test = rb.getAllReminders();

        if (test.isEmpty()) {
            noReminder.setVisibility(View.VISIBLE);
        }

        // Create recycler view
       /* recycleList.setLayoutManager(getLayoutManager());
        registerForContextMenu(recycleList);
        SimpleAdapter adapter = new SimpleAdapter();
        adapter.setItemCount(getDefaultItemCount());
        recycleList.setAdapter(adapter);*/

        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddReminderActivity.class);
                startActivity(intent);
            }
        });
        alarmReceiver = new AlarmReceiver();
    }

}
