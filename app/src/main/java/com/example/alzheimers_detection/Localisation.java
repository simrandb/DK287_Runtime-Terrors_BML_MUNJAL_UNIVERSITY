package com.example.alzheimers_detection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.Locale;

public class Localisation{
 Locale myLocale;
//String currentLanguage = "en";
    int currLang;
    Context context;

    static int currentLang;

    public Localisation(Context c){
        context = c;
    }


    public void changelanguage(String lang){

        switch (lang) {
            case "en": setLocale("en",1);
                break;
            case "hi": setLocale("hi",2);
                break;
        }
        Toast.makeText(context, "lang = "+lang,Toast.LENGTH_LONG).show();

        if(lang != "") {
        SharedPreferences sharedpreferences = context.getSharedPreferences("prefs",
        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("language", lang);
        editor.commit();
        }
    }


public void setLocale(String localeName, int localnum) {
        String s = null;
        // if (currLang != localnum) {
        myLocale = new Locale(localeName);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent(context,context.getClass());

        refresh.putExtra(s, localeName);
        context.startActivity(refresh);
        currLang = localnum;
        // } else {
        //Toast.makeText(MainActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        //}
        }


}


