package com.example.alzheimers_detection;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class VI_Start extends AppCompatActivity {
    Locale myLocale;
    //String currentLanguage = "en";
    int currLang;
    Spinner spinner ;
    static int currentLang;
    Speak sp;
    private static Boolean done = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vi_start);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        sp = new Speak(this.getApplicationContext());
        speak(getString(R.string.VI_Instructions));
        Button button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (done) {
                    Intent myIntent=new Intent(getApplicationContext(), VI_Memory_intro.class);
                    startActivity(myIntent);
                }

            }
        });
    }

    void speak(String say){
        //call speakOut function
        sp.speakOut(say); //need minimum api level 21

        //to slow down the speed
        sp.changeSpeed(0.9f);

        //set Progresslistener to keep track of tts
        sp.getListener().setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
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
                        done = true; //speaker has finished speaking
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
}

       /* // getActionBar().hide();
        spinner = findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(this.getString(R.string.selectlanguage));
        arrayList.add(this.getString(R.string.english));
        arrayList.add(this.getString(R.string.hindi));


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 1:
                        setLocale("en",1);
                        break;
                    case 2:
                        setLocale("hi",2);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        Button btn = findViewById(R.id.button);

       btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent myIntent=new Intent(getApplicationContext(), VI_Memory_intro.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myIntent);

            }
        });
    }


        public void setLocale(String localeName, int localnum) {
            String s = null;
          // if (currLang != localnum) {
                myLocale = new Locale(localeName);
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(this, VI_Start.class);
                refresh.putExtra(s, localeName);
                startActivity(refresh);
                currLang = localnum;
           // } else {
                //Toast.makeText(VI_Start.this, "Language already selected!", Toast.LENGTH_SHORT).show();
            //}
        }

        public void onBackPressed() {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }

}
*/