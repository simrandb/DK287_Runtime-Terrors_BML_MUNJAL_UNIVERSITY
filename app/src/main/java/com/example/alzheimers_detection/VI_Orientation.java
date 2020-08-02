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
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class VI_Orientation extends AppCompatActivity implements RecognitionListener {

    private Speak tts;
    private SpeechRecognizer speech;
    private static final int REQUEST_RECORD_PERMISSION = 100;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private boolean done;
    private LinearLayout screen;
    private TextView result;
    String question;
    String[] answers;
    private TextView st;
    int c;
    int score = 0;






    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vi_orientation);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        //tts = new TextToSpeech(this, this);
        //tts.setSpeechRate(0.7f);
        //tts.setPitch(0.2f); // to change pitch

        askPermission();

        setSpeechRecognition();
        tts = new Speak(VI_Orientation.this);

        c=-1;
        answers = new String[6];
        result = findViewById(R.id.result);
        screen = findViewById(R.id.wholeScreen);
        st = findViewById(R.id.statement);

        function();

        //event for button SPEAK button
        screen.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (done && c<=6 && c>=0) {
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
        done =false;
        result.setText("");
        question = selectQuestion();
        textToSpeech(question);
        if(c>-1)
            st.setText(question);

    }

    //Speak class object
    void textToSpeech(String text) {

        //call speakOut function
        tts.speakOut(text); //need minimum api level 21

        //to slow down the speed
        tts.changeSpeed(0.7f);

        //set Progresslistener to keep track of tts
        tts.getListener().setOnUtteranceProgressListener(new UtteranceProgressListener() {
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
                        Toast.makeText(getApplicationContext(), "Done ", Toast.LENGTH_SHORT).show();
                        if( c != -1)
                            done = true; //speaker has finished speaking
                        else {
                            c++;
                            new CountDownTimer(2000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    function();
                                }
                            }.start();
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
    }
    private String selectQuestion() {

        String ques="";

        switch(c){

            case -1: String name = "John"; //fetch user name from db here
                ques ="Hello "+name+", "+(getResources().getString(R.string.intro));
                break;

            case 0: ques = getResources().getString(R.string.Q1);
                break;

            //Q2
            case 1: ques = getResources().getString(R.string.Q2);
                break;

            //Q3
            case 2: ques = getResources().getString(R.string.Q3);
                break;

            //Q4
            case 3: ques = getResources().getString(R.string.Q4);
                break;

            //Q5
            case 4: ques = getResources().getString(R.string.Q5);
                break;

            //Q6
            case 5: ques = getResources().getString(R.string.Q6);
                break;

        }
        return ques;
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

        String res = matches.get(0).trim();
        if(!res.isEmpty()) {
            result.setText(res);
            answers[c] = res;
            c++;
        }

        if(c<=6){
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    function();
                }
            }.start();
        } else if(c>6) {
            calculateScore();
            new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {

                    //nextActivity();

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
        //Toast.makeText(MainActivity.this, "onBeginningOfSpeech", Toast
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
                (VI_Orientation.this,
                        new String[]{Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
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
                    Toast.makeText(VI_Orientation.this, "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }

    private void calculateScore(){

        Calendar cal = Calendar.getInstance();

        String arr1[] = {"one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve",
                "thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen","twenty","twenty-one","twenty-two",
                "twenty-three","twenty-four","twenty-five","twenty-six","twenty-seven","twenty-eight","twenty-nine","thirty","thirty-one"};
        String arr2[] = {"first","second","third","fourth","fifth","sixth","seventh","eighth","ninth","tenth","eleventh","twelfth",
                "thirteenth","fourteenth","fifteenth","sixteenth","seventeenth","eighteenth","nineteenth","twentieth","twenty-first","twenty-second",
                "twenty-third","twenty-fourth","twenty-fifth","twenty-sixth","twenty-seventh","twenty-eighth","twenty-ninth","thirtieth","thirty-first"};

        String arr3[] = {"1st","2nd","3rd","4th","5th","6th","7th","8th","9th","10th","11th","12th","13th","14th","15th","16th","17th",
                "18th","19th","20th","21st","22nd","23rd","24th","25th","26th","27th","28th","29th","30th","31st"};
        String arr4[] = {"i","ii","iii","iv","v","vi","vii","viii","ix","x","xi","xii","xiii","xiv","xv","xvi","xvii","xviii",
                "xix","xx","xxi","xxii","xxiii","xxiv","xxv","xxvi","xxvii","xxviii","xxix","xxx","xxxi"};
        //to check date
        int date = cal.get(Calendar.DATE);
        if(answers[0].equalsIgnoreCase(Integer.toString(date))
                || answers[0].equalsIgnoreCase(arr1[date-1])
                || answers[0].equalsIgnoreCase(arr2[date-1])
                || answers[0].equalsIgnoreCase(arr3[date-1])
                || answers[0].equalsIgnoreCase(arr4[date-1]));
        score++;

        //to check month
        if(answers[1].equalsIgnoreCase(Integer.toString(cal.get(Calendar.MONTH) + 1)));
        score++;

        //to check year
        if(answers[2].equalsIgnoreCase(Integer.toString(cal.get(Calendar.YEAR))))
            score++;

        //to check day of a week
        String week[] = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        if(answers[3].equalsIgnoreCase(week[cal.get(Calendar.DAY_OF_WEEK) - 1]))
            score++;

        String address[] = getAddress().split(" ");

        //to check country
        if(address[2].equalsIgnoreCase(answers[4]))
            score++;

        //to check city
        if(address[0].equalsIgnoreCase(answers[5]))
            score++;

        TextView sc = findViewById(R.id.score);
        sc.setText("Score: " + Integer.toString(score));
    }

    String getAddress(){
        LocationAPI location = new LocationAPI(VI_Orientation.this);
        String address = "";
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            address = location.getLocation().trim();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;

    }

    /*private void nextActivity(){

     fuser = mAuth.getCurrentUser();
    uid=fuser.getUid();
    dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
        dbUsers.child("orientation").setValue(score);

        Intent myIntent=new Intent(getApplicationContext(),VI_Verbal_fluency_intro.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myIntent);
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        VI_Sentence_Repetition.this.finish();
    }*/
}