package com.example.singi_000.prototype1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.singi_000.testmultiplescreens.R;


public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //puts a button in that will issue a notification and make a new tab
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //send a notification
                //setNotificationTitle("Open second inscription!");
              //  mNotifyMgr.notify(1, mBuilder.build());

                //send some information about the notification to the inscription activity
                // and switch to the new tab
                Intent i = new Intent(v.getContext(), InscriptionActivity.class);
                i.putExtra("ID", 1);
                startActivity(i);

                //disable the button
                button.setEnabled(false);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }


}
