package com.example.singi_000.prototype1;

/**
 * LanguageFields.java (clumsy, I know)
 * @author Benji Weichman
 * @version February 7th, 2015
 *
 * This class isolates all the language-sensitive fields for a particular Inscription.
 * Each InscriptionLanguageVersion instance corresponds to one language.
 */

public class LanguageFields {

    private String name;
    private String locationDescription;
    private String historicalInfo;
    private String inscriptionTranslation;

    public LanguageFields(String name, String locationDescription,
                          String historicalInfo, String inscriptionTranslation){

        this.name = name;
        this.locationDescription = locationDescription;
        this.historicalInfo = historicalInfo;
        this.inscriptionTranslation = inscriptionTranslation;
    }

    public String getName(){
        return name;
    }

    public String getLocationDescription(){
        return locationDescription;
    }

    public String getHistoricalInfo(){
        return historicalInfo;
    }

    public String getInscriptionTranslation(){
        return inscriptionTranslation;
    }
}
