package com.example.singi_000.prototype1;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;

import com.example.singi_000.testmultiplescreens.R;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class BaseActivity extends Activity {


    // access to settings database
    SharedPreferences prefs;

    long [] vibratePattern = {500, 500, 500, 500};

    NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.abc_list_selector_holo_dark)
                    .setContentTitle("My notification")
                    .setContentText("Click to view this inscription!")
                    .setAutoCancel(true);

    Intent resultIntent;



    NotificationManager mNotifyMgr;

    BackEndConnection beConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        prefs = getSharedPreferences("global_preferences", 0);
        setUpNotification();
        beConnection = new BackEndConnection(this.getApplicationContext(), this);

    }

    public void onMenuButtonClick(View v){
        openOptionsMenu();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.item1: //Inscriptions
                i = new Intent(this, InscriptionActivity.class);
                startActivity(i);
                return true;
            case R.id.item2: //List
                i = new Intent(this, ListActivity.class);
                startActivity(i);
                return true;
            case R.id.item3: //Search
                i = new Intent(this, SearchActivity.class);
                startActivity(i);
                return true;
            case R.id.item4: //Map
                i = new Intent(this, MapActivity.class);
                startActivity(i);
                return true;
            case R.id.item5: //Settings
                i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.item6: //Help
                i = new Intent(this, HelpActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setUpNotification(){

        resultIntent = new Intent(this, InscriptionActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Log.d("DEBUGGER", getSharedPreferences("global_preferences", 0).getString("notification_sound", "NULL"));

        setNotificationSound(prefs.getString("notification_sound", "Silent"));

        Log.d("DEBUGGER", getSharedPreferences("global_preferences", 0).getString("notification_sound", "NULL"));
    }


    /**
     * Append an inscription ID to the InscriptionData.txt file
     * @param num
     */
    public void appendNumToInscriptionData(int num) {
        try {
            //updating the new current tab
            FileOutputStream fos =openFileOutput("InscriptionCurrData.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fos);
            outputWriter.write(num + "\n");
            outputWriter.close();
            fos.close();

            //adding the tab to the list of tabs
            FileOutputStream fos2=openFileOutput("InscriptionData.txt", MODE_APPEND);
            OutputStreamWriter outputWriter2 = new OutputStreamWriter(fos2);
            outputWriter2.write(num + "\n");
            outputWriter2.close();
            fos2.close();

        }
        catch (Exception e) {

        }
    }

    public void setNotificationSound(String setting) {

        if(setting.equals("Sound")) {
            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mBuilder.setVibrate(vibratePattern);
        }
        else if(setting.equals("Vibrate")) {
            mBuilder.setSound(null);
            mBuilder.setVibrate(vibratePattern);
        }
        else if(setting.equals("Silent")) {
            mBuilder.setSound(null);
        }
    }
}
