package com.example.singi_000.prototype1;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;

import com.example.singi_000.testmultiplescreens.R;


public class BaseActivity extends Activity {

/*
    // Build a basic notification - code from Android development documents
    NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.abc_list_selector_holo_dark)
                    .setContentTitle("My notification")
                    .setContentText("Click to view this inscription!");

    Intent resultIntent = new Intent(this, InscriptionActivity.class);
    // Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.

    NotificationManager mNotifyMgr;

    //final TabHost tabhost = (TabHost) findViewById(R.id.tabHost);


    public void setUpNotification(){

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    //---------------------------------------------------------------//

    public void setNotificationTitle(String title){
        mBuilder.setContentTitle(title);
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //setUpNotification();
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
}
