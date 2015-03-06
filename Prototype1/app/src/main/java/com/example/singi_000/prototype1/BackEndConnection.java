package com.example.singi_000.prototype1;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Observable;
import java.util.Observer;

public class BackEndConnection implements Observer {

    private Context context;
    private BaseActivity activity;

    /**
     * Constructor for a back end connection object, to facilitate the passing of updates from the
     * back end to the front end.
     * @param ctx context
     * @param act current activity
     */
    public BackEndConnection(Context ctx, BaseActivity act) {
        context = ctx;
        activity = act;
    }

    /**
     * The method the back end will call to send a notification to the front end.
     * @param obs the observable object (defined by the back end)
     * @param idNum the ID of the inscription to be opened
     */
    public void update(Observable obs, Object idNum) {

        int ID = (int) idNum;

        try {
            //set the output stream to append the new ID
            activity.appendNumToInscriptionData(ID);

            //send a notification -- will be able to add more details here, such as thumbnail,
            //once we have the concrete methods from the back end
            activity.mBuilder.setContentTitle("Entered geofence for ID: " + ID);
            activity.mNotifyMgr.notify(ID, activity.mBuilder.build());



        }
        catch(Exception e) {
            Log.e(activity.getLocalClassName(), "Error in notification from back end");
        }
    }



}