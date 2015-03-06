package com.example.singi_000.prototype1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.singi_000.testmultiplescreens.R;

public class SettingsActivity extends BaseActivity {



    public class NotificationSpinnerActivity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            String selection = (String) parent.getItemAtPosition(pos);

            SharedPreferences preferencesObject = getSharedPreferences("global_preferences", 0);
            SharedPreferences.Editor prefEditor = preferencesObject.edit();



            if(selection.equals("Sound")) {

                prefEditor.putString("notification_sound", selection);
            }
            else if(selection.equals("Vibrate")) {

                prefEditor.putString("notification_sound", selection);
            }
            else if(selection.equals("Silent")) {
                prefEditor.putString("notification_sound", selection);
            }

            prefEditor.commit();

            setNotificationSound(selection);




        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        // set up spinner for selecting notification sound
        Spinner notificationSpinner = (Spinner) findViewById(R.id.notificationSound);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.notification_sounds, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        notificationSpinner.setAdapter(adapter);
        notificationSpinner.setOnItemSelectedListener(new NotificationSpinnerActivity());
        //set the spinner to display the most recent selection
        notificationSpinner.setSelection(adapter.getPosition(getSharedPreferences("global_preferences", 0).getString("notification_sound", "Silent")));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }






}
