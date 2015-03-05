package com.example.singi_000.prototype1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TabHost;
import android.view.View;
import android.widget.DatePicker;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.example.singi_000.testmultiplescreens.R;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class InscriptionActivity extends BaseActivity {


    private final static int MAX_TABS = 8;
    private int[] tabInscriptionKeys=new int[MAX_TABS];
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

        loadTabs();
        for(int i=0; i<MAX_TABS; i++){
            if(i<numTabsOpen) {
                tabs[i].setVisibility(View.VISIBLE);
            }
            else{
                tabs[i].setVisibility(View.INVISIBLE);
            }
        }
        if(numTabsOpen>0) {
            switchTab(currTab);
        }


        //setting up the current colors of the tabs for testing
        for(int i=0; i<MAX_TABS; i++){
            tabs[i].setBackgroundColor(Color.LTGRAY);
        }
        if(numTabsOpen>0) {
            tabs[currTab].setBackgroundColor(Color.BLUE);
        } else {
            disableTabElements(true);
        }



    }

    /**
     * This method saves the current state of the tabs (which inscription tabs are open and which
     * one is currently selected) to a text file that can be read to reopen the tabs when switching
     * between activities or reopening the app.
     */
    public void saveTabs(){
        try {
            //saving the order of tabs
            FileOutputStream fileout=openFileOutput("InscriptionData.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            for (int i = numTabsOpen-1; i >=0; i--) {
                outputWriter.write(tabInscriptionKeys[i] + "\n");
            }
            outputWriter.close();
            //saving the current tab
            fileout=openFileOutput("InscriptionCurrData.txt", MODE_PRIVATE);
            outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(tabInscriptionKeys[currTab] + "\n");
            outputWriter.close();

        }catch(IOException e){
            //This shouldn't happen since the file does exist and is writable.
            //I'm not sure what sort of error handling should be placed here.
        }
    }

    /**
     *
     */
    public void loadTabs(){
        try {
            //loading the current tab
            FileInputStream fileIn=openFileInput("InscriptionCurrData.txt");
            InputStreamReader inputRead= new InputStreamReader(fileIn);
            int firstChar=inputRead.read();
            if(firstChar==-1){
                //the file is empty, so there are no tabs open
                numTabsOpen=0;
                currTab=0;
                return;
            }
            char currChar=(char)firstChar;
            String st = "";
            while(currChar!='\n'){
                st+=currChar;
                currChar=(char)inputRead.read();
            }
            int currTabInscriptionKey=Integer.valueOf(st);

            //loading the list of tabs
            fileIn=openFileInput("InscriptionData.txt");
            inputRead= new InputStreamReader(fileIn);
            Stack<Integer> keys = new Stack<Integer>();
            boolean done=false;
            while(!done){
                int readChar=inputRead.read();
                if(readChar==-1){
                    done=true;
                }
                else {
                    currChar = (char) readChar;
                    st = "";
                    while (currChar != '\n') {
                        st += currChar;
                        currChar = (char) inputRead.read();
                    }
                    if(st.length()>0) {
                        keys.push(Integer.valueOf(st));
                    }
                }
            }
            inputRead.close();

            //first, figuring out if the currTab will be one of the 8 open, if not making it 0
            currTab=keys.search(currTabInscriptionKey)-1;

            if(currTab<0 || currTab>MAX_TABS-1){
                currTab=0;
            }
            numTabsOpen=0;
            ArrayList<Integer> usedKeys=new ArrayList<Integer>();
            while(numTabsOpen<MAX_TABS &&!keys.empty()){
                    int nextKey=keys.pop();
                    if(!usedKeys.contains(nextKey)) {
                        tabInscriptionKeys[numTabsOpen] = nextKey;
                        usedKeys.add(tabInscriptionKeys[numTabsOpen]);
                        numTabsOpen++;
                    }
                }

        } catch (IOException e) {
            System.out.println("FILE NOT FOUND ");
            //This shouldn't happen since the file does exist and is writable.
            //I'm not sure what sort of error handling should be placed here.
        }

        if (numTabsOpen == 0){
            disableTabElements(true);
        } else {
            disableTabElements(false);
        }

    }

    /**
     * This method responds to the click of a specific tab by switching all of the Inscription
     * fields to those of the Inscription corresponding to that tab. It also switches which tab
     * button is currently highlighted.
     * @param tabNum - the number corresponding to the tab clicked
     */
    public void switchTab(int tabNum){
        //for now, it just switches the text field to the current tab number
        tabs[currTab].setBackgroundColor(Color.LTGRAY);
        currTab=tabNum;
        tabs[currTab].setBackgroundColor(Color.BLUE);
        TextView t=new TextView(this);
        t=(TextView)findViewById(R.id.tabExampleText);
        System.out.println("inscription #: "+tabInscriptionKeys[tabNum]);
        t.setText("Inscription #"+tabInscriptionKeys[tabNum]);
        saveTabs();

        //FILL IN CODE HERE TO CHANGE INSCRIPTION INFORMATION
    }

    /**
     * This method opens up a new tab for an inscription by its key. If the inscription is already
     * open, it just moves it to another tab. Otherwise, it adds it to the top and moves all other
     * tabs down, possibly removing the bottom tab if there are already the maximum number of tabs.
     *
     * @param inscriptionKey - the key of the inscription to be opened
     */
    public void openTab(int inscriptionKey){
        if (numTabsOpen == 0){
            disableTabElements(false);
        }
        //making sure this inscription isn't open yet
        for(int i=0; i<numTabsOpen; i++){
            if(tabInscriptionKeys[i]==inscriptionKey){
                //the tab is already open, so we just want to move it to the top
                for(int j=i; j>0; j--){
                    tabInscriptionKeys[j]=tabInscriptionKeys[j-1];
                }
                tabInscriptionKeys[0]=inscriptionKey;
                saveTabs();
                switchTab(0);
                return;
            }
        }
        for(int i=MAX_TABS-1; i>0; i--){
            tabInscriptionKeys[i]=tabInscriptionKeys[i-1];
        }
        tabInscriptionKeys[0]=inscriptionKey;
        if(numTabsOpen<MAX_TABS){
            numTabsOpen++;
            tabs[numTabsOpen-1].setVisibility(View.VISIBLE);
        }
        switchTab(0);
        saveTabs();
    }


    /**
     * This method allows a tab to be closed. It moves up all tabs below it and removes the current
     * inscription tab.
     * @param view
     */
    public void closeTab(View view){
        if(numTabsOpen>0) {
            for (int i = currTab; i < MAX_TABS - 1; i++) {
                tabInscriptionKeys[i] = tabInscriptionKeys[i + 1];
            }
            tabInscriptionKeys[MAX_TABS - 1] = -1;
            numTabsOpen--;
            tabs[numTabsOpen].setVisibility(View.INVISIBLE);
            if (currTab < numTabsOpen) {
                switchTab(currTab);
            } else {
                if (currTab > 0) {
                    currTab--;
                    switchTab(currTab);
                } else {
                    //FILL IN CODE HERE TO HANDLE NO MORE INSCRIPTION TABS BEING OPEN
                    //All tabs have been closed;
                    //Fill all inscription fields with nothing
                    //Make title "No Inscriptions Open"
                }
            }
            saveTabs();
        }

        if(numTabsOpen==0){
            disableTabElements(true);
        }
        saveTabs();
    }


    /**
     * Sets visibility of all inscription elements to @param visibility
     * Sets visibility of the notice that no tabs are open to !(@param visibility).
     * (e.g. pass "false" to hide elements + display notice)
     *
     * @param disable Desired usability state of inscription elements.
     */
    public void disableTabElements(boolean disable){
      int tabElements, noTabsNotice;
      boolean tabElemsClick;

        if (disable){
            tabElements = View.INVISIBLE;
            noTabsNotice = View.VISIBLE;
            tabElemsClick = false;
        } else {
            tabElements = View.VISIBLE;
            noTabsNotice = View.INVISIBLE;
            tabElemsClick = true;
        }

        // hide the "close tab" button
        Button closeTabButton = (Button)findViewById(R.id.button2);
        closeTabButton.setVisibility(tabElements);
        closeTabButton.setClickable(tabElemsClick);

        // hide the tabs
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        for(int i=0; i<linearLayout.getChildCount(); i++){
            View v = linearLayout.getChildAt(i);
            v.setVisibility(tabElements);
            v.setClickable(tabElemsClick);
        }

        // hide the body elements
        linearLayout = (LinearLayout) findViewById(R.id.inscription_tab_body);
        for(int i=0; i<linearLayout.getChildCount();i++){
            View v = linearLayout.getChildAt(i);
            if (v instanceof TextView && v.getId() == R.id.tabs_closed_notice){
                v.setVisibility(noTabsNotice);
            } else {
                v.setClickable(tabElemsClick);
                v.setVisibility(tabElements);
            }
        }

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
