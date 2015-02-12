package com.example.singi_000.testmultiplescreens;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
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
