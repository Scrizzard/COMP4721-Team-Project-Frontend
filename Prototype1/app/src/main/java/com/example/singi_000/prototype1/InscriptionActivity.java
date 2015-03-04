package com.example.singi_000.prototype1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TabHost;
import android.view.View;

import com.example.singi_000.testmultiplescreens.R;


public class InscriptionActivity extends BaseActivity {

    String [] iTitle = {"Inscription 1", "Inscription 2", "Inscription 3"};
    String [] iInfo = {"Inscription 1 Information", "Inscription 2 Information", "Inscription 3 Information"};

    private Inscription [] tabsOpen;
    private int numTabsOpen;
    private final static int MAX_TABS = 8;

    private Button closeTabBtn;

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

    public int addTab(String tag, String inscriptionName, int tabNum, int inscriptionID, Bitmap thumbnail, float difficulty,
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
            setInfo(iInfo[tabNum]);

            /* ++ update all inscription page settings ++ */

            return 0;
        }
        else
            return -1;
    }

    public int closeCurrentTab(TabHost tabHost){

        int i = tabHost.getCurrentTab();
        Inscription currInscription = tabsOpen[i];

        System.out.println(i);

        // If the last tab is closed, return to the main screen
        if (numTabsOpen == 1){
            tabsOpen[i] = null;
            numTabsOpen--;
            Intent intent = new Intent(this, BaseActivity.class);
            tabHost.getTabWidget().removeView(tabHost.getTabWidget().getChildTabViewAt(i));
            startActivity(intent);
            return -1;
        // Otherwise, close the current tab and shift the elements of the array
        // so that the next opened tab will be opened at index numTabsOpen
        } else {
            if (i != numTabsOpen -1){
                tabsOpen[i] = null;
                for(int j=i; j<numTabsOpen-1;j++){
                    tabsOpen[j+1] = tabsOpen[j];
                }
            }
            tabHost.getTabWidget().removeView(tabHost.getTabWidget().getChildTabViewAt(i));
            numTabsOpen--;
            return i;
        }
    }

    //---------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        if(tabsOpen == null)
            tabsOpen = new Inscription[MAX_TABS];
        if(numTabsOpen < 1)
            numTabsOpen = 0;


        //set up tabhost, possibly for the first time
        final TabHost tabhost = (TabHost) findViewById(R.id.tabHost);

        closeTabBtn = (Button) findViewById(R.id.button2);
        closeTabBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               closeCurrentTab(tabhost);
            }
        });

        // get passed intent, which may contain the instruction to contruct a new tab
        Intent intent = getIntent();

        // get message value from intent, if no new tab should be added, -1
        int ID = intent.getIntExtra("ID", -1);

        //just some filler numbers, which will eventually be provided by the back end
        float [] coords = {1,1};

        addTab("tab1","Inscription 1", 0, 0, null, 1, coords, 1, iInfo[0]);
        addTab("tab" + 1, "Inscription 2", 1, 0, null, 1, coords, 1, iInfo[1]);

        // eventually would open up a tab for whatever inscription ID was passed. For Prototype 1
        // this just makes a new tab for inscription 2 and sets it as the current one
        if(ID != -1) {
            addTab("tab" + ID, "Inscription 2", 1, 0, null, 1, coords, 1, iInfo[ID]);
            tabhost.setCurrentTab(1);
        }

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






    }

    //---------------------------------------------------------------//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inscription, menu);
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
