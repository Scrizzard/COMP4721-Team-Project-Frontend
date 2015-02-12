package com.example.singi_000.testmultiplescreens;

import java.sql.Timestamp;
import java.util.Date;

//import org.junit.Before;

/**
 * Preference.java
 * @author Benji Weichman
 * @version February 7th, 2015
 *
 * This class stores the user-specific settings for a single inscription.
 *
 * I use the Timestamp subclass of Date because it has the features we need and it's designed
 *  to work well with databases. Note that most fields can't be changed, since all changes
 *  should be applied directly to the database (not this shallow copy).
 */
public class Preference {


    //id -- unique identifier for a particular inscription
    private int id;
    //opened -- whether the tab has been selected before
    private boolean opened;
    //found -- whether the user claimed they found the inscription
    private boolean found;
    //neverNotify -- whether the user wants notifications from this inscription
    private boolean neverNotify;
    //timeoutSet -- whether the user set a notification timeout for this inscription
    private boolean timeoutSet;

    //timeoutLimit -- timestamp after which we may generate notifications again
    private Timestamp timeoutLimit;
    //dateFound -- when the inscription was most recently found by this user
    private Timestamp dateFound;
    //lastNotified -- the last time we notified the user about this inscription
    private Timestamp lastNotified;

    /**
     * Constructor.
     * Preferences are only initialized by the database manager upon a fetch request.
     * */
    public Preference(int id, boolean opened, boolean found, boolean neverNotify,
                      Timestamp timeoutLimit, Timestamp dateFound, Timestamp lastNotified){

        this.id = id;
        this.opened = opened;
        this.found = found;
        this.neverNotify = neverNotify;
        this.timeoutSet = timeoutLimit != null;

        this.timeoutLimit = timeoutLimit;
        this.dateFound = dateFound;
        this.lastNotified = lastNotified;
    }

    public int getId(){
        return id;
    }

    public boolean getOpened(){
        return opened;
    }

    public boolean getFound(){
        return found;
    }

    public Timestamp getDateFound(){
        return new Timestamp(dateFound.getTime());

    }

    public Timestamp getDateLastNotified(){
        return new Timestamp(lastNotified.getTime());
    }

    public boolean getBlocked(){
        return neverNotify || isInTimeout();
    }

    /**
     * Determine whether the inscription is temporarily blocked from being notified.
     */
    private boolean isInTimeout(){

        //error case, should never be possible
        if(timeoutSet && timeoutLimit == null){
            System.out.println("data problem in inscription " + id +
                    ":\n  there should be a timeout set, but the field is null" +
                    "\n  I'll assume notifications for this inscription are blocked");
            return true;
        }

        if(timeoutSet && timeoutLimit != null){
            Date date = new Date();
            Timestamp now = new Timestamp(date.getTime());
            return timeoutLimit.after(now);
        }

        return false;
    }
}
