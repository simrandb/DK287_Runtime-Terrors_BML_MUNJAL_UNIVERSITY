package com.example.alzheimers_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AskForJournal extends AppCompatActivity {
    Button gotoresults,gotojournal;
    ImageView journal;
    Button b;
    Bitmap bitmap;
    public FirebaseAuth mAuth;
    DatabaseReference dbUsers;
    FirebaseUser fuser;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_for_journal);
        mAuth = FirebaseAuth.getInstance();

        journal=findViewById(R.id.journalb);
       // b=findViewById(R.id.b);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();



        gotojournal=findViewById(R.id.gotojournal);
        gotoresults=findViewById(R.id.gotoresults);

        gotojournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuser = mAuth.getCurrentUser();
                uid=fuser.getUid();
                dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                dbUsers.child("behaviouralResultOrNot").setValue(1);
                Intent i=new Intent(getApplicationContext(),JournalQuestions.class);
                startActivity(i);
            }
        });

        gotoresults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuser = mAuth.getCurrentUser();
                uid=fuser.getUid();
                dbUsers= FirebaseDatabase.getInstance().getReference("Users/"+uid);
                dbUsers.child("behaviouralResultOrNot").setValue(0);

                Intent i=new Intent(getApplicationContext(),HomeScreen.class);
                startActivity(i);

            }
        });
        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    journal.setDrawingCacheEnabled(true);

                bitmap = Bitmap.createBitmap(journal.getDrawingCache());

                Log.d("testtagg",""+bitmap);

            }
        });

*/
    }
}