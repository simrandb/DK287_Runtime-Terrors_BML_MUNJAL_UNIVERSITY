package com.example.alzheimers_detection;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Calendar;

public class Orientation extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private int year, month, day;
    int score;
    int seconds=3;
    String loc;
    String stage_name,username;
    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;
    String description;
    OnSwipeTouchListener onSwipeTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orientation);

        final DatabaseReference userDBRef = FirebaseDatabase.getInstance().getReference("Users");

        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();

        userDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(uid).getValue(User.class);
                username=user.getFirstname();
                seconds=3;
                final Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(seconds>0)
                        {
                            seconds=seconds-1;
                            handler.postDelayed(this,1000);
                        }
                        else
                        {

                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
            }
        });

        loc = getAddress().trim();
        stage_name="Orientation";
        if(username==null)
        {
            username="xyz";
        }

        description = "\nYou are "+username+", the Chief Editor of “The Alzheimer’s Times “! An incomplete Newspaper format" +
                " \nwill be shown.\nClick on the highlighted fields to select the appropriate details and complete the" +
                " format.";


        onSwipeTouchListener = new OnSwipeTouchListener(this, findViewById(R.id.Orientation),stage_name);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        final ListsForSpinner list = new ListsForSpinner();
        final boolean[] stateSet = {false};

        Intent intent = getIntent();
        final String Play = intent.getStringExtra("Play");

        if(Play==null||Play.contains("no"))
        {
            new CountDownTimer(1000,1000){

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    PopUp_PlayGame p = new PopUp_PlayGame();
                    p.showPopUp(Orientation.this,description);
                }
            }.start();

        }


        //date textview listener
        final TextView dateView = (TextView) findViewById(R.id.textView31);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog =  new DatePickerDialog(Orientation.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,
                        2013,5,15);


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yyyy, int mm, int dd) {

                mm++;
                dateView.setText(dd + "/" + mm + "/" + yyyy);
                year = yyyy;
                month = mm;
                day = dd;

            }
        };

        //day textview listener
        final TextView dayTv = findViewById(R.id.textView33);
        final String[] weekDays = list.weekDays();

        dayTv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater =
                        (LayoutInflater)getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.orientation_popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
                Button cancel = (Button)popupView.findViewById(R.id.cancel);
                TextView tv = popupView.findViewById(R.id.popuptv);
                tv.setText("Select a Day");
                final Spinner popupSpinner = (Spinner)popupView.findViewById(R.id.daysSpinner);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(Orientation.this,
                                android.R.layout.simple_spinner_item, weekDays);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                popupSpinner.setAdapter(adapter);
                popupView.findViewById(R.id.okay).setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dayTv.setText(popupSpinner.getSelectedItem().toString());
                        popupWindow.dismiss();
                    }});
                cancel.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});
                popupWindow.showAsDropDown(dayTv, 50, -30);

            }
        });

        //listener for state
        final TextView stateTv = findViewById(R.id.textView34);
        final String[] states = list.states();
        stateTv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater =
                        (LayoutInflater)getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.orientation_popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
                Button cancel = (Button)popupView.findViewById(R.id.cancel);
                TextView tv = popupView.findViewById(R.id.popuptv);
                tv.setText("Select a State");
                final Spinner popupSpinner = (Spinner)popupView.findViewById(R.id.daysSpinner);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(Orientation.this,
                                android.R.layout.simple_spinner_item, states);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                popupSpinner.setAdapter(adapter);
                popupView.findViewById(R.id.okay).setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        stateTv.setText(popupSpinner.getSelectedItem().toString());
                        popupWindow.dismiss();
                        stateSet[0] = true;
                    }});
                cancel.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});
                popupWindow.showAsDropDown(dayTv, 50, -30);


            }
        });

        //listener for city
        final TextView cityTv = findViewById(R.id.textView29);

        cityTv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if(!stateSet[0]) {
                    Toast toast=Toast.makeText(getApplicationContext(),"Select a State first",Toast.LENGTH_LONG);
                    toast.show();

                } else {

                    String state = stateTv.getText().toString().trim();
                    final String[] cities = list.getCitiesArray(state);

                    LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext()
                                    .getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.orientation_popup, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    popupWindow.setOutsideTouchable(true);
                    Button cancel = popupView.findViewById(R.id.cancel);
                    TextView tv = popupView.findViewById(R.id.popuptv);
                    tv.setText("Select a City");
                    final Spinner popupSpinner = (Spinner) popupView.findViewById(R.id.daysSpinner);
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(Orientation.this,
                                    android.R.layout.simple_spinner_item, cities);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    popupSpinner.setAdapter(adapter);
                    popupView.findViewById(R.id.okay).setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cityTv.setText(popupSpinner.getSelectedItem().toString());
                            popupWindow.dismiss();
                        }
                    });
                    cancel.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(dayTv, 50, -30);

                }
            }
        });

        Button publish = findViewById(R.id.publish);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_activity();
            }
        });

    }

    private void next_activity() {

        calculateScore();
        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
        dbUsers.child("orientation").setValue(score);
        Popup_aftergame panel = new Popup_aftergame();
        panel.showPopUp(Orientation.this, stage_name);

        Intent i=new Intent(getApplicationContext(), ImmediateRecall_Intro.class);
        startActivity(i);


    }

    void calculateScore(){
        Calendar cal = Calendar.getInstance();

        if(year == cal.get(Calendar.YEAR))
            score++;

        if(month == cal.get(Calendar.MONTH) + 1)
            score++;

        if(day == cal.get(Calendar.DATE))
            score++;

        TextView dayOfWeek = findViewById(R.id.textView33);
        ListsForSpinner list = new ListsForSpinner();
        String week[] = list.weekDays();
        if(dayOfWeek.getText().toString().equalsIgnoreCase(week[cal.get(Calendar.DAY_OF_WEEK) - 1]))
            score++;


        String address[];
        if(loc.length() == 0){
            address = (getAddress().split(" "));
        }else{
            address = loc.split(" ");
        }
        if(address.length >= 3) {
            TextView state = findViewById(R.id.textView34);
            if(address[1].equalsIgnoreCase(state.getText().toString()))
                score++;

            TextView city = findViewById(R.id.textView29);
            if(address[0].equalsIgnoreCase(city.getText().toString()))
                score++;
        }
        Toast.makeText(this,Integer.toString(score),Toast.LENGTH_LONG).show();


    }

    String getAddress(){
        LocationAPI location = new LocationAPI(Orientation.this);
        String address = "";
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
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
}