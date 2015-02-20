package com.example.singi_000.prototype1;

/**
 * Inscription.java
 * @author Benji Weichman
 * @version February 7th, 2015
 *
 * This class holds the user-immutable information about an inscription.
 */

import android.graphics.Bitmap;
//import java.awt.Image;
import java.util.HashMap;

public class Inscription {

    // id -- inscription-specific id
    private int id;
    // difficulty -- [someone from the front end can describe this]
    private float difficulty;
    // coordinates -- lat/long pair that describe an inscription's location
    private float[] coordinates;
    //radius -- size of the inscription's notification zone
    private float radius;
    // inscriptionLatin -- original untranslated inscription text
    private String inscriptionLatin;
    // thumbnail -- scaled down image of inscription
    //private Image thumbnail;
    private Bitmap thumbnail;

    // hashmap from languages to language-specific inscription fields
    HashMap<Language, LanguageFields> languageSensitiveFields;

    public Inscription(int id, /*Image thumbnail*/ Bitmap thumbnail, float difficulty,
                       float[] coordinates, float radius, String inscriptionLatin) {

        this.id = id;
        this.difficulty = difficulty;
        this.coordinates = coordinates;
        this.radius = radius;
        this.inscriptionLatin = inscriptionLatin;
        this.thumbnail = thumbnail;

        languageSensitiveFields = new HashMap<Language, LanguageFields>();

        if (coordinates == null || coordinates.length != 2) {
            System.out.println("data problem in Inscription with id " + id
                    + "\n  coordinate vector is null or not a 2-tuple"
                    + "\n  setting coordinates to null");
            this.coordinates = null;
        }
    }

    /**
     * Note that overwriting existing translations is prevented.
     */
    public void addLanguageVersion(Language lingo,
                                   LanguageFields translatedFields) {
        if (!languageSensitiveFields.containsKey(lingo)) {
            languageSensitiveFields.put(lingo, translatedFields);
        }
    }

    public int getId() {
        return id;
    }

    public float getDifficulty() {
        return difficulty;
    }

    // be careful to return a shallow copy here
    public float[] getCoordinates() {
        if (coordinates == null) {
            return null;
        }

        return new float[] { coordinates[0], coordinates[1] };
    }

    public float getRadius(){
        return radius;
    }

    public String getInscriptionLatin() {
        return inscriptionLatin;
    }

    // TODO: I can't write this yet.
    // I need to know the concrete image type the front end wants to use.
    // Otherwise I can't return a deep copy here.
    public /*Image*/ Bitmap getImage() {
        return null;
    }

    public String getName(Language lingo) {
        LanguageFields fields = languageSensitiveFields.get(lingo);

        if (fields != null) {
            return fields.getName();
        }

        return null;
    }

    public String getLocationDescription(Language lingo) {
        LanguageFields fields = languageSensitiveFields.get(lingo);

        if (fields != null) {
            return fields.getLocationDescription();
        }

        return null;
    }

    public String getHistoricalInfo(Language lingo) {
        LanguageFields fields = languageSensitiveFields.get(lingo);

        if (fields != null) {
            return fields.getHistoricalInfo();
        }

        return null;
    }

    public String getInscriptionTranslation(Language lingo) {
        LanguageFields fields = languageSensitiveFields.get(lingo);

        if (fields != null) {
            return fields.getInscriptionTranslation();
        }

        return null;
    }
}
