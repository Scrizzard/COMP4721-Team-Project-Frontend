package com.example.singi_000.prototype1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TabHost;

import android.support.v4.app.NotificationCompat.Builder;

import com.example.singi_000.testmultiplescreens.R;


public class MainActivity extends ActionBarActivity {

    public String iTitle1 = "Inscription 1";
    public String iInfo1 = "Inscription 1: This is the textual explanation of how to find the inscription. " +
            "We assume that this will be multi-lined and fairly lengthly in some cases, but smaller in others.";

    public String iTitle2 = "Inscription 2";
    public String iInfo2 = "Inscription 2: This is the textual explanation of how to find the inscription. " +
            "We assume that this will be multi-lined and fairly lengthly in some cases, but smaller in others.";

    public String iTitle3 = "Inscription 3";
    public String iInfo3 = "Inscription 3: This is the textual explanation of how to find the inscription. " +
            "We assume that this will be multi-lined and fairly lengthly in some cases, but smaller in others.";


    private Inscription [] tabsOpen;
    private int numTabsOpen;
    private final static int MAX_TABS = 8;


    /** Build a basic notification - code from Android development documents */
    Builder mBuilder =
          new Builder(this)
          .setSmallIcon(R.drawable.abc_list_selector_holo_dark)
          .setContentTitle("My notification")
          .setContentText("Click to view this inscription!");

    Intent resultIntent = new Intent(this, MainActivity.class);
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

    //sets the title in the tab
    public void setTitle(String titleText){
        TextView title = (TextView) findViewById(R.id.titleView);
        title.setText(titleText);
    }

    //---------------------------------------------------------------//

    //sets the info in the tab
    public void setInfo(String infoText){
        TextView info = (TextView) findViewById(R.id.infoView);
        info.setText(infoText);
    }

    //---------------------------------------------------------------//

    public void setNotificationTitle(String title){
        mBuilder.setContentTitle(title);
    }

    //---------------------------------------------------------------//

    private int addTab(String tag, String inscriptionName, int tabNum, int inscriptionID, Bitmap thumbnail, float difficulty,
                       float [] coordinates, float radius, String inscriptionLatin){

        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);

        if(numTabsOpen < MAX_TABS) {
            tabhost.setup();
            TabHost.TabSpec ts = tabhost.newTabSpec(tag);
            ts.setContent(R.id.tab);
            ts.setIndicator(inscriptionName);
            tabhost.addTab(ts);
            tabsOpen[numTabsOpen] = new Inscription(1, null, 1, coordinates, 1, inscriptionLatin);
            numTabsOpen++;
            setInfo(iInfo1);

            /* ++ update inscription page settings ++ */

            return 0;
        }
        else
            return -1;
    }


    //---------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up hashmap to keep track of tabs
        //tabsOpen = new HashMap<Integer, Inscription>();
        tabsOpen = new Inscription[MAX_TABS];
        numTabsOpen = 0;

        //initialize anything needed for notifications
        setUpNotification();

        //set up tabhost for the first time
        final TabHost tabhost = (TabHost) findViewById(R.id.tabHost);


        float [] coords = {1,1};
        addTab("tab1","Inscription 1", 0, 0, null, 1, coords, 1, iInfo1);

        //set up how changing a tab modifies the information displayed
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                int i = tabhost.getCurrentTab();
                Inscription currInscription = tabsOpen[i];

                /* ++ HERE IS WHERE WE'D ADD METHODS TO SWITCH TO THE INFORMATION ABOUT A DIFFERENT
                INSCRIPTION ++ */

                setInfo(currInscription.getInscriptionLatin());
                //we'd have to check here if there were any buttons we needed to enable, since
                //they might be disabled on a different screen

            }
        });


        //puts a button in that will issue a notification and make a new tab
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //send a notification
                setNotificationTitle("Open second inscription!");
                mNotifyMgr.notify(1, mBuilder.build());

                float [] coords = {1,1};
                addTab("tab2", "Inscription " + (numTabsOpen + 1), 0, 0, null, 1, coords, 1, iInfo2);
                tabhost.setCurrentTab(1);

                //disable the button
                button.setEnabled(false);
            }
        });



    }

    //---------------------------------------------------------------//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //---------------------------------------------------------------//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
