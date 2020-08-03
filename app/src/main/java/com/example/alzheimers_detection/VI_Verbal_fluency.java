package com.example.alzheimers_detection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VI_Verbal_fluency extends AppCompatActivity implements RecognitionListener {

    private TextView clk;
    private SpeechRecognizer speech;
    private static final int REQUEST_RECORD_PERMISSION = 100;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private boolean stopSpeech = false;
    private ArrayList<String> allWords;
    private LinearLayout screen;
    private TextView result;
    char letter;
    private Speak tts;
    int correctWords;


    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vi_verbal_fluency);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        askPermission();
        mAuth = FirebaseAuth.getInstance();

        clk = findViewById(R.id.clock);
        allWords = new ArrayList<>();
        result = findViewById(R.id.result);
        screen = findViewById(R.id.wholeScreen);


        letter = 'A';

        final boolean[] timerOn = {false};
        //event for button SPEAK button
        screen.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(!stopSpeech) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            speech.stopListening();
                            break;

                        case MotionEvent.ACTION_DOWN:
                            if(!timerOn[0]) {
                                setTimer(); //60sec timer
                                timerOn[0] = true;
                                setSpeechRecognition();
                            }
                            speech.startListening(recognizerIntent);
                            break;
                    }
                } else {
                    if(motionEvent.getAction() == MotionEvent.ACTION_BUTTON_PRESS){
                        tts = new Speak(getApplicationContext());
                        tts.speakOut("Time is over");

                    }
                }

                return false;
            }


        });


    }


   /* private void selectLetter(){
        String s = "";
        int i = (int)((Math.random())*100) % 3;

        switch (i) {
            case 0 : s = getResources().getString(R.string.Text1A);
                letter = 'A';
                break;

            case 1:  s = getResources().getString(R.string.Text1F);
                letter = 'F';
                break;

            case 2:  s = getResources().getString(R.string.Text1S);
                letter = 'S';
                break;
        }

        TextView st = findViewById(R.id.statement);

        tts.speakOut(s);
        st.setText(s);
    } */

    void setSpeechRecognition(){
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 2);

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        speech.setRecognitionListener(this);

    }


    @Override
    public void onResults(Bundle results) {

        Log.i(LOG_TAG, "onResults");

        //getting all the matches
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        String[] s = matches.get(0).trim().split(" ");

        for(int i=0; i<s.length; i++){
            if(s[i] != " ") {
                if (!Character.isUpperCase(s[i].charAt(0))
                        && Character.toUpperCase(s[i].charAt(0)) == letter
                        && !allWords.contains(s[i])) { // condition to check proper nouns, first character and repetition

                    correctWords++;
                }
                allWords.add(s[i]);
                result.append(s[i] + "\n");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);

    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        Log.i(LOG_TAG, "onBufferReceived: " + bytes);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");

    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        switch (errorMessage){
            case "No match" :
                tts = new Speak(this);
                tts.speakOut("Press the screen again.");
                Toast.makeText(getApplicationContext(), "Press the screen again. ", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPartialResults(Bundle results) {
        Log.i(LOG_TAG, "onPartialResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        Log.i(LOG_TAG, "onPartialResults="+matches.get(0));

    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onEvent");
    }

    public String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    //to check/ask recording permission
    void askPermission(){
        ActivityCompat.requestPermissions
                (VI_Verbal_fluency.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(VI_Start.this, "Permission Granted!", Toast
                    //   .LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VI_Verbal_fluency.this, "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }

    void setTimer(){
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                clk.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                onStop();
                speech.destroy();
                // tts.destroy();
                stopSpeech = true;
                screen.setBackgroundColor(getResources().getColor(R.color.Black));

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {

                        fuser = mAuth.getCurrentUser();
                        uid=fuser.getUid();
                        dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                        dbUsers.child("fluency").setValue(correctWords);

                        Intent myIntent=new Intent(getApplicationContext(),VI_Abstraction_intro.class);
                        startActivity(myIntent);
                        VI_Verbal_fluency.this.finish();
                    }
                }.start();

            }
        }.start();
    }
}
