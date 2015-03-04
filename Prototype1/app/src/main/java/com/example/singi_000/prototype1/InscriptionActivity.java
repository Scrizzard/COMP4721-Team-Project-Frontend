package com.example.singi_000.prototype1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TabHost;
import android.view.View;

import com.example.singi_000.testmultiplescreens.R;


public class InscriptionActivity extends BaseActivity {


    private final static int MAX_TABS = 8;
    private int[] tabInscriptionKeys=new int[MAX_TABS];
    private boolean[] tabOpen=new boolean[MAX_TABS];
    private Button[] tabs=new Button[MAX_TABS];
    private int currTab=0;
    private int numTabsOpen;

    int testTabNum=50;


    //---------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        //filling up the button array
        for(int i=0; i<MAX_TABS; i++){
                String buttonID = "tab" + (i+1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                tabs[i] = ((Button) findViewById(resID));
            final int tabNum=i;
            tabs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchTab(tabNum);
                }
            });
        }

        //here is where I need to add loading in from file

        tabs[currTab].setBackgroundColor(Color.BLUE);

        //filling up with example inscription numbers
        for(int i=0; i<MAX_TABS; i++){
            tabs[i].setBackgroundColor(Color.LTGRAY);
            tabInscriptionKeys[i]=i+20;
            tabOpen[i]=true;
        }
        numTabsOpen=8;


    }

    public void switchTab(int tabNum){
        //this method will go through and switch out all of the inscription stuff to the next one
        //for now, it just switches the text field to the current tab number
        tabs[currTab].setBackgroundColor(Color.LTGRAY);
        currTab=tabNum;
        tabs[currTab].setBackgroundColor(Color.BLUE);
        TextView t=new TextView(this);
        t=(TextView)findViewById(R.id.tabExampleText);
        t.setText(""+tabInscriptionKeys[tabNum]);
    }

    public void openTab(int inscriptionKey){
        //making sure this inscription isn't open yet
        for(int i=0; i<MAX_TABS; i++){
            if(tabInscriptionKeys[i]==inscriptionKey){
                //the tab is already open, so we just want to move it to the top
                for(int j=i; j>0; j--){
                    tabInscriptionKeys[j]=tabInscriptionKeys[j-1];
                    tabOpen[j]=tabOpen[j-1];
                }
                tabInscriptionKeys[0]=inscriptionKey;
                tabOpen[0]=true;
                return;
            }
        }
        for(int i=MAX_TABS-1; i>0; i--){
            tabInscriptionKeys[i]=tabInscriptionKeys[i-1];
            tabOpen[i]=tabOpen[i-1];
        }
        tabInscriptionKeys[0]=inscriptionKey;
        tabOpen[0]=true;
        if(numTabsOpen<MAX_TABS){
            numTabsOpen++;
            tabs[numTabsOpen-1].setVisibility(View.VISIBLE);
        }
        switchTab(0);
    }

    public void testTabOpen(View view) {
        openTab(testTabNum++);
    }

    public void closeTab(View view){
        for(int i=currTab; i<MAX_TABS-1; i++){
            tabInscriptionKeys[i]=tabInscriptionKeys[i+1];
            tabOpen[i]=tabOpen[i+1];
        }
        tabInscriptionKeys[MAX_TABS-1]=-1;
        tabOpen[MAX_TABS-1]=false;
        for(int i=0; i<MAX_TABS; i++){
            if(!tabOpen[i]){
                tabs[i].setVisibility(View.INVISIBLE);
            }
        }
        if(tabOpen[currTab]) {
            switchTab(currTab);
        }
        else{
            //checking to make sure there's a tab left;
            if(currTab-1>-1) {
                switchTab(currTab - 1);
            }
        }
        numTabsOpen--;
    }

    //---------------------------------------------------------------//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inscription, menu);
        return true;
    }

    //---------------------------------------------------------------//

}
