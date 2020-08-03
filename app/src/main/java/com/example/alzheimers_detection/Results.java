package com.example.alzheimers_detection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.ImageFormat;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.util.Base64.DEFAULT;

public class Results extends AppCompatActivity implements View.OnClickListener{
    static String filename;
    float executivefunctioningScore,memoryScore,sentenceRepetitionScore,namingScore,abstractionScore,calculationScore,orientationScore,immediaterecallScore,attentionScore,visuoperceptionScore,fluencyScore,delayedrecallScore;
    String progressTableAspects,behavioural_info,family_behavioural_info;
    int seconds=1,behaviouralResultOrNot=0;// if 1 , user has played behavioural questions .
    String uid;
    Button buttonQrc;
    int count=0;
    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    Double totscore;
    float totalFamilyHistoryScore,totalBehaviouralScore;
    TextView totalscore;
    Bitmap bitmap;
    int numOfPrevScores;
    int prevScoreArray[]=new int[5];
    String prevDateArray[]=new String[5];
    ArrayList<Element> previousScoresList;
    User user;
    int VI;
    LinearLayout graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        previousScoresList = new ArrayList<>();

        FirebaseUser fuser;
        mAuth = FirebaseAuth.getInstance();
        //graph=findViewById(R.id.graph);
        //graph.setDrawingCacheEnabled(true);

        fuser = mAuth.getCurrentUser();
        uid=fuser.getUid();
        dbUsers= FirebaseDatabase.getInstance().getReference("Users");
        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TextView statement = findViewById(R.id.result_statement);
                totalscore=findViewById(R.id.totalscore);

                user = dataSnapshot.child(uid).getValue(User.class);
                VI=user.getVI();
                if (VI==0)
                {
                    abstractionScore=user.getAbstraction();
                    behaviouralResultOrNot=user.getBehaviouralResultOrNot();
                    attentionScore=user.getAttention();
                    namingScore=user.getNaming();
                    calculationScore=user.getCalculation();
                    orientationScore=user.getOrientation();
                    immediaterecallScore=user.getImmediateRecall();
                    delayedrecallScore=user.getDelayedRecall();
                    visuoperceptionScore=user.getVisuoperception();
                    fluencyScore=user.getFluency();
                    executivefunctioningScore=user.getExecutiveFunctioning();
                    totalBehaviouralScore=user.getTotalBehaviouralScore();
                    totalFamilyHistoryScore=user.getTotalFamilyHistoryScore();
                    family_behavioural_info=user.getFamily_behavioural_info();
                    behavioural_info=user.getBehavioural_info();
                    progressTableAspects=user.getProgressTableAspects();



                    if(behaviouralResultOrNot==0)//If user has played optional behavioural stage
                    {
                        Log.d("Excet",""+executivefunctioningScore);
                        Log.d("abst",""+abstractionScore);
                        Log.d("atten",""+attentionScore);

                        totscore = (double) (abstractionScore + attentionScore + calculationScore + namingScore + visuoperceptionScore + delayedrecallScore + orientationScore + fluencyScore + executivefunctioningScore);
                        totscore = ((0.97) * totscore) + (0.03 * totalFamilyHistoryScore);
                        Log.d("orientation",""+orientationScore);
                        totalscore.setText("Total Score :"+ Math.round(((totscore/30)*100)*100)/100+"%");


                        if(totscore > 25){      //value 20 is not verified
                            statement.setText(getResources().getString(R.string.result2));
                        } else {
                            statement.setText(getResources().getString(R.string.result1));

                        }


                    }
                    else if (behaviouralResultOrNot==1){//User hasnt played behavioural stage
                        totscore = (double) (abstractionScore + attentionScore + calculationScore + namingScore + visuoperceptionScore + delayedrecallScore + orientationScore + fluencyScore + executivefunctioningScore);
                        totscore = ((0.95) * totscore) + (0.03 * totalFamilyHistoryScore) + (0.02 * totalBehaviouralScore);
                        totalscore.setText("Total Score :"+ Math.round(((totscore/30)*100)*100)/100+"%");

                        if(totscore > 25){//value 20 is not verified-simran changed it to greater than 25
                            statement.setText(getResources().getString(R.string.result2));
                        } else {
                            statement.setText(getResources().getString(R.string.result1));

                        }
                    }
                    setBar(findViewById(R.id.set1), (int)((executivefunctioningScore/1)*100), R.color.fill_5, R.color.empty_5, "Executive Functioning");
                    setBar(findViewById(R.id.set2), (int)((namingScore/4)*100), R.color.fill_4, R.color.empty_4, "Naming");

                    setBar(findViewById(R.id.set3), (int)((abstractionScore/3)*100), R.color.fill_3, R.color.empty_3, "Abstraction");

                    setBar(findViewById(R.id.set4), (int)((calculationScore/3)*100), R.color.fill_2, R.color.empty_2, "Calculation");

                    setBar(findViewById(R.id.set5),(int)((orientationScore/6)*100), R.color.fill_1, R.color.empty_1,"Orientation");

                    setBar(findViewById(R.id.set6), (int)((immediaterecallScore/2)*100), R.color.fill_5, R.color.empty_5,"Immediate Recall");

                    setBar(findViewById(R.id.set7), (int)((attentionScore/3)*100), R.color.fill_4, R.color.empty_4, "Attention");

                    setBar(findViewById(R.id.set8), (int)((visuoperceptionScore/3)*100), R.color.fill_3, R.color.empty_3,"Visuoperception");

                    setBar(findViewById(R.id.set9), (int)((fluencyScore/2)*100), R.color.fill_2, R.color.empty_2,"Fluency");

                    setBar(findViewById(R.id.set10),(int)((delayedrecallScore/3)*100), R.color.fill_1, R.color.empty_1, "Delayed Recall");


                }
                else
                {
                    abstractionScore=user.getAbstraction();
                    behaviouralResultOrNot=user.getBehaviouralResultOrNot();
                    attentionScore=user.getAttention();
                    calculationScore=user.getCalculation();
                    orientationScore=user.getOrientation();
                    delayedrecallScore=user.getDelayedRecall();
                    fluencyScore=user.getFluency();
                    totalBehaviouralScore=user.getTotalBehaviouralScore();
                    totalFamilyHistoryScore=user.getTotalFamilyHistoryScore();
                    family_behavioural_info=user.getFamily_behavioural_info();
                    behavioural_info=user.getBehavioural_info();
                    progressTableAspects=user.getProgressTableAspects();
                    sentenceRepetitionScore=user.getSentenceRepetition();
                    memoryScore=user.getMemory();

                    Log.d("abstrscore",""+abstractionScore);
                    Log.d("uid",""+uid);

                    /*seconds=5;
                    final Handler handler=new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });*/
                    if(behaviouralResultOrNot==0)//
                    {
                        totscore = (double) (abstractionScore + attentionScore + calculationScore + memoryScore + sentenceRepetitionScore + delayedrecallScore + orientationScore + fluencyScore );
                        totscore = ((0.97) * totscore) + (0.03 * totalFamilyHistoryScore);
                        totalscore.setText("Total Score :"+ Math.round(((totscore/30)*100)*100)/100+"%");


                        if(totscore > 20){      //value 20 is not verified
                            statement.setText(getResources().getString(R.string.result2));
                        } else {
                            statement.setText(getResources().getString(R.string.result1));

                        }

                    }
                    else {//User hasnt played behavioural stage
                        totscore = (double) (abstractionScore + attentionScore + calculationScore + memoryScore + sentenceRepetitionScore + delayedrecallScore + orientationScore + fluencyScore );
                        totscore = ((0.95) * totscore) + (0.03 * totalFamilyHistoryScore) + (0.02 * totalBehaviouralScore);
                        totalscore.setText("Total Score :"+ Math.round(((totscore/30)*100)*100)/100+"%");
                        totalscore.setText("Total :");

                        if(totscore > 25){//value 20 is not verified-simran changed it to greater than 25
                            statement.setText(getResources().getString(R.string.result2));
                        } else {
                            statement.setText(getResources().getString(R.string.result1));

                        }
                    }
                }








                numOfPrevScores =user.getNumOfScores();

                Log.d("numofprev",""+numOfPrevScores);


  /* if(numOfPrevScores==1) userDBRef.child(uid).child("Score1").setValue(((float)Math.round(totscore * 100) / 100));
                else if(numOfPrevScores==2) userDBRef.child(uid).child("Score2").setValue(((float)Math.round(totscore * 100) / 100));
                else if(numOfPrevScores==3) userDBRef.child(uid).child("Score3").setValue(((float)Math.round(totscore * 100) / 100));
                else if(numOfPrevScores==4) userDBRef.child(uid).child("Score4").setValue(((float)Math.round(totscore * 100) / 100));
                else userDBRef.child(uid).child("Score5").setValue(((float)Math.round(totscore * 100) / 100));*/


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //ALL PREV SCORE VALUES HAVE COME INTO ARRAY
        //testing successful
        //Toast.makeText(getApplicationContext()," "+abstractionScore+" "+attentionScore+" "+executivefunctioningScore,Toast.LENGTH_LONG).show();


        seconds=5;
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
                    int index = (numOfPrevScores-1)%5;
                    String tag = "score" + (index + 1);
                    dbUsers.child(uid).child(tag).setValue(((float)Math.round(totscore * 100) / 100));

                    prevScoreArray[0]=user.getScore1();
                    prevScoreArray[1]=user.getScore2();
                    prevScoreArray[2]=user.getScore3();
                    prevScoreArray[3]=user.getScore4();
                    prevScoreArray[4]=user.getScore5();

                    Calendar cal = Calendar.getInstance();
                    String dateToday = cal.get(Calendar.DATE) +"/"+ (cal.get(Calendar.MONTH) + 1) +"/"+ cal.get(Calendar.YEAR);
                    String tagDate = "date" + (index + 1);
                    dbUsers.child(uid).child(tagDate).setValue(dateToday);

                    prevDateArray[0]=user.getDate1();
                    prevDateArray[1]=user.getDate2();
                    prevDateArray[2]=user.getDate3();
                    prevDateArray[3]=user.getDate4();
                    prevDateArray[4]=user.getDate5();


                    for( int i=0; i < 5; i++){
                        if(prevScoreArray[i] != -1){
                            previousScoresList.add(new Element(prevScoreArray[i],prevDateArray[i]));
                        } else{
                            break;
                        }
                    }

                    //below code is for testing purpose only since no data is generated yet
                   /* if(previousScoresList.isEmpty()){
                        previousScoresList.add(new Element(50,"1. 10/1/2020"));
                        previousScoresList.add(new Element(100,"2. 11/2/2020"));
                        previousScoresList.add(new Element(100,"3. 27/5/2020"));
                    }*/

                    if (VI==0) {
                        progressTableAspects = progressTableAspects + ("\nTrial " + (int) numOfPrevScores + "     " + (int) executivefunctioningScore + " " + (int) namingScore + " " + (int) abstractionScore + " " + (int) calculationScore + " " + (int) orientationScore + " " + (int) immediaterecallScore + " " + (int) attentionScore + " " + (int) visuoperceptionScore + " " + (int) fluencyScore + " " + (int) delayedrecallScore + " " + ((float) Math.round(totscore * 100) / 100));
                        dbUsers.child(uid).child("progressTableAspects").setValue(progressTableAspects);

                        if (behaviouralResultOrNot == 1) {
                            dbUsers.child(uid).child("textFile").setValue(family_behavioural_info + behavioural_info + progressTableAspects);
                        } else {
                            dbUsers.child(uid).child("textFile").setValue(family_behavioural_info + progressTableAspects);
                        }
                    }

                    else
                    {
                        progressTableAspects = progressTableAspects + ("\nTrial " + (int) numOfPrevScores + "     " + (int) memoryScore + " " + (int) attentionScore + " " + (int) calculationScore + " " + (int) sentenceRepetitionScore + " " + (int) fluencyScore + " " + (int) abstractionScore + " " + (int) delayedrecallScore + " " + (int) orientationScore +" " + ((float) Math.round(totscore * 100) / 100));
                        dbUsers.child(uid).child("progressTableAspects").setValue(progressTableAspects);

                        if (behaviouralResultOrNot == 1) {
                            dbUsers.child(uid).child("textFile").setValue(family_behavioural_info + behavioural_info + progressTableAspects);
                        } else {
                            dbUsers.child(uid).child("textFile").setValue(family_behavioural_info + progressTableAspects);
                        }
                    }

                }
                if(count == 0) {
                    setScoringBoard();
                    count++;
                }

            }
        });

        //previous scores
        findViewById(R.id.previousResults).setOnClickListener(this);

        //line graph button
        findViewById(R.id.buttonGraph).setOnClickListener(this);

        //previous scores
        findViewById(R.id.buttonQrc).setOnClickListener(this);

        //line graph button
        findViewById(R.id.buttonHome).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String filename = ""+uid+".png";
        File sd = Environment.getExternalStorageDirectory();
        File dest = new File(sd+filename);
        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.previousResults :
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.scoring_popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);


                final ScrollView layout = findViewById(R.id.scorescreen);

                //to set height and width of a popup
                int height = layout.getHeight();
                popupWindow.setHeight(height / 2);
                    /*int width = layout.getWidth();
                    popupWindow.setWidth(2*width/3);*/


                Button cancel = (Button) popupView.findViewById(R.id.button_back);

                // Lookup the recyclerview in activity layout
                RecyclerView rvContacts = popupView.findViewById(R.id.rvContacts);

                // Initialize contacts
                //ArrayList<Element> scoreList = Element.createScoreList(numOfPrevScores);
                // Create adapter passing in the sample user data
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(previousScoresList);
                // Attach the adapter to the recyclerview to populate items
                rvContacts.setAdapter(adapter);
                // Set layout manager to position the items
                rvContacts.setLayoutManager(new LinearLayoutManager(Results.this));
                // That's all!

                cancel.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                break;


            case R.id.buttonGraph:
                LayoutInflater layoutInflater2 =
                        (LayoutInflater) getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView2 = layoutInflater2.inflate(R.layout.linegraph_popup, null);
                final PopupWindow popupWindow2 = new PopupWindow(
                        popupView2, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow2.setOutsideTouchable(true);


                final ScrollView layout2 = findViewById(R.id.scorescreen);

                //to set height and width of a popup
                int height2 = layout2.getHeight();
                popupWindow2.setHeight(height2 / 2);
                int width2 = layout2.getWidth();
                popupWindow2.setWidth(4*width2/5);
                setLineGraph(popupView2);

                Button back = (Button) popupView2.findViewById(R.id.button_back);


                back.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow2.dismiss();

                    }
                });

                popupWindow2.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
                break;


            case R.id.buttonQrc: Intent i=new Intent(getApplicationContext(),QRC.class);
                startActivity(i);
                break;

            case R.id.buttonHome: Intent i2=new Intent(getApplicationContext(),HomeScreen.class);
                startActivity(i2);
                break;

        }
    }
    void setScoringBoard(){

        setBar(findViewById(R.id.set1), (int)((executivefunctioningScore/1)*100), R.color.fill_5, R.color.empty_5, "Executive Functioning");
        Toast.makeText(this,""+abstractionScore,Toast.LENGTH_LONG).show();
        setBar(findViewById(R.id.set2), (int)((namingScore/4)*100), R.color.fill_4, R.color.empty_4, "Naming");

        setBar(findViewById(R.id.set3), (int)((abstractionScore/3)*100), R.color.fill_3, R.color.empty_3, "Abstraction");

        setBar(findViewById(R.id.set4), (int)((calculationScore/3)*100), R.color.fill_2, R.color.empty_2, "Calculation");

        setBar(findViewById(R.id.set5),(int)((orientationScore/6)*100), R.color.fill_1, R.color.empty_1,"Orientation");

        setBar(findViewById(R.id.set6), (int)((immediaterecallScore/2)*100), R.color.fill_5, R.color.empty_5,"Immediate Recall");

        setBar(findViewById(R.id.set7), (int)((attentionScore/3)*100), R.color.fill_4, R.color.empty_4, "Attention");

        setBar(findViewById(R.id.set8), (int)((visuoperceptionScore/3)*100), R.color.fill_3, R.color.empty_3,"Visuoperception");

        setBar(findViewById(R.id.set9), (int)((fluencyScore/2)*100), R.color.fill_2, R.color.empty_2,"Fluency");

        setBar(findViewById(R.id.set10),(int)((delayedrecallScore/3)*100), R.color.fill_1, R.color.empty_1, "Delayed Recall");

    }

    void setBar(View set, int score, int fc, int ec, String stagename){
        ProgressBar horizontalProgressBar =set.findViewById(R.id.horizontal_progress_bar);
        TextView name = set.findViewById(R.id.title);
        TextView sc = set.findViewById(R.id.percent);

        name.setText(stagename);
        sc.setText(score + "%");
        int fillColor = ContextCompat.getColor(this,fc);
        int emptyColor = ContextCompat.getColor(this, ec);
        int separatorColor = ContextCompat.getColor(this, R.color.transparent);

        SegmentedProgressDrawable progressDrawable = new SegmentedProgressDrawable(3, fillColor, emptyColor, separatorColor);
        horizontalProgressBar.setProgressDrawable(progressDrawable);
        horizontalProgressBar.setProgress(score);
    }

    void setLineGraph (View v) {
        LineChart mChart = v.findViewById(R.id.chart);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        Description des = new Description();
        des.setText(" ");
        mChart.setDescription(des);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.getXAxis().setTextSize(10f);
        mChart.getXAxis().setTextColor(getResources().getColor(R.color.rowshape));

        mChart.getAxisLeft().setTextSize(15f);
        mChart.getAxisRight().setTextSize(0f);
        mChart.getAxisLeft().setTextColor(getResources().getColor(R.color.rowshape));

        ArrayList<Entry> values = new ArrayList<>();

        int size = previousScoresList.size();
        for(int i=0; i<size; i++){
            values.add(new Entry(i+1, previousScoresList.get(i).getScoreInt()));
        }


        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, " ");
            set1.setDrawIcons(false);
            // set1.enableDashedLine(10f, 5f, 0f);
            //set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.RED);
            set1.setCircleColor(getResources().getColor(R.color.rowshape));

            set1.setLineWidth(2f);
            set1.setCircleRadius(6f);
            set1.setValueTextColor(Color.WHITE);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(10.f);
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.redcolor
                );
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLUE);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
        }

        mChart.setDrawGridBackground(false);
    }

}