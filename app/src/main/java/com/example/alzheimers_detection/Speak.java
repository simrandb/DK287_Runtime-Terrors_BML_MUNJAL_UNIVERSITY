package com.example.alzheimers_detection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Locale;

public class Speak implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    Context c;
    String text;

    public Speak(Context c){
        this.c = c;
    }

    TextToSpeech getListener(){
        return tts;
    }

    //tts = new TextToSpeech(this, this);
    //tts.setSpeechRate(0.7f);
    //tts.setPitch(0.2f); // to change pitch

    void changeSpeed(float r){
        tts.setSpeechRate(r);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void speakOut(String text) {
        tts = new TextToSpeech(c, this);
        tts.setSpeechRate(0.7f);
        this.text = text;
        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "text");
    }

    public void destroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            String lang="";
                SharedPreferences sharedpreferences = c.getSharedPreferences("prefs",
                        Context.MODE_PRIVATE);

                if (sharedpreferences.contains("language")) {
                    lang = sharedpreferences.getString("language", "").trim();
                    //Toast.makeText(c, "lang = "+lang,Toast.LENGTH_LONG).show();
                }
                if (lang == "")
                    lang = "en";
                int result = tts.setLanguage(new Locale(lang));
            //int result = tts.setLanguage(new Locale("hi"));
           // int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                if (text == null || text.isEmpty())
                    return;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String utteranceId = this.hashCode() + "";
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                } else {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        } else {
            Log.e("TTS", "Initialisation Failed!");
        }
    }

}


