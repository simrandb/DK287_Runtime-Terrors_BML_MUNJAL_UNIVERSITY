package com.example.alzheimers_detection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class VI_Sentence_Repetition extends AppCompatActivity implements TextToSpeech.OnInitListener, RecognitionListener {

    private TextToSpeech tts;
    private SpeechRecognizer speech;
    private static final int REQUEST_RECORD_PERMISSION = 100;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private int done;
    private LinearLayout screen;
    private TextView result;
    int score;
    String sentence;
    int selectedSentence;
    private TextView st;
    int c;







    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vi_sentence_repetition);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        //tts = new TextToSpeech(this, this);
        //tts.setSpeechRate(0.7f);
        //tts.setPitch(0.2f); // to change pitch

        askPermission();

        setSpeechRecognition();
        tts = new TextToSpeech(this, this);
        tts.setSpeechRate(0.7f);
        c=0;
        done=0;
        result = findViewById(R.id.result);
        screen = findViewById(R.id.wholeScreen);
        selectedSentence = -1;
        st = findViewById(R.id.statement);

        sentence = selectSentence();
        st.setText(sentence);
        speakOut(sentence);

        //event for button SPEAK button
        screen.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (done==2 && c<2) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            speech.stopListening();
                            break;

                        case MotionEvent.ACTION_DOWN:
                            speech.startListening(recognizerIntent);
                            break;
                    }
                }
                return false;
            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void function(){
        done=0;
        result.setText("");
        sentence = selectSentence();
        st.setText(sentence);
        speakOut(sentence);
    }


    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.US);

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }
            else{
                if(sentence == null || sentence.isEmpty())
                    return;

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    String utteranceId=this.hashCode() + "";
                    tts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                }
                else{
                    tts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
        else{
            Log.e("TTS", "Initialisation Failed!");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speakOut(String text) {
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                final String keyword = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "Started" + keyword, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onDone(String s) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "Done ", Toast.LENGTH_SHORT).show();
                        done ++;
                        if(done==1){
                            speakOut(sentence);
                        }
                    }
                });
            }

            @Override
            public void onError(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, "text");
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private String selectSentence() {
        String s = "";
        int i;
        do {
            i = ((int) (Math.random() * 100)) % 8;
        }while(i == selectedSentence);

        selectedSentence = i;

        switch (i) {
            case 0: s = getResources().getString(R.string.sentence1);
                break;

            case 1: s = getResources().getString(R.string.sentence2);
                break;

            case 2: s = getResources().getString(R.string.sentence3);
                break;

            case 3: s = getResources().getString(R.string.sentence4);
                break;

            case 4: s = getResources().getString(R.string.sentence5);
                break;

            case 5: s = getResources().getString(R.string.sentence6);
                break;

            case 6: s = getResources().getString(R.string.sentence7);
                break;

            case 7: s = getResources().getString(R.string.sentence8);
                break;
        }

        return s;
    }

    void setSpeechRecognition() {
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

        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        String res = matches.get(0).trim()+".";

        if(res.equalsIgnoreCase(sentence))
        {
            score++;
        }
        if(!matches.get(0).trim().isEmpty()) {
            result.setText(res);
            c++;
        }

        if(c==1){
            new CountDownTimer(3000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                public void onFinish() {
                    function();
                }
            }.start();
        } else  if(c==2) {
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    nextActivity();
                }
            }.start();
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
        //Toast.makeText(VI_Start.this, "onBeginningOfSpeech", Toast
        //         .LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        switch (errorMessage) {
            case "No match":
                final Speak sp = new Speak(this.getApplicationContext());
                sp.speakOut("Press the screen again.");
                sp.getListener().setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {
                        final String keyword = s;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getApplicationContext(), "Started" + keyword, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onDone(String s) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getApplicationContext(), "Done ", Toast.LENGTH_SHORT).show();
                                sp.destroy();
                            }
                        });
                    }

                    @Override
                    public void onError(String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                Toast.makeText(getApplicationContext(), "Press the screen again. ", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPartialResults(Bundle results) {
        Log.i(LOG_TAG, "onPartialResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        Log.i(LOG_TAG, "onPartialResults=" + matches.get(0));

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
    void askPermission() {
        ActivityCompat.requestPermissions
                (VI_Sentence_Repetition.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(VI_Sentence_Repetition.this, "Permission Granted!", Toast
                    //  .LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VI_Sentence_Repetition.this, "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }

    private void nextActivity(){

        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
        dbUsers.child("sentenceRepetition").setValue(score);

        Intent myIntent=new Intent(getApplicationContext(),VI_Verbal_fluency_intro.class);
        startActivity(myIntent);
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        VI_Sentence_Repetition.this.finish();
    }
}


