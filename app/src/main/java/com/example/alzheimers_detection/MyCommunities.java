package com.example.alzheimers_detection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MyCommunities extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_people);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

    }

    public void website(View view) {
        String url ="";
        switch (view.getId()) {
            case R.id.textView10:
                url = "https://www.alz.org/help-support/community";
                break;

            case R.id.TextView19:
                url = "https://www.alzconnected.org/";
                break;

            case R.id.TextView21:
                url = "https://www.caring.com/alzheimers-support";
                break;

            case R.id.textView23:
                url = "https://www.alzconnected.org/";
                break;

            case R.id.textView25:
                url = "https://knightadrc.wustl.edu/";
                break;

            case R.id.textView27:
                url = "https://www.healingwell.com/community/default.aspx?f=8#gsc.tab=0";
                break;

            case R.id.textView29:
                url = "https://www.dementiaguide.com/";
                break;

            case R.id.textView31:
                url = "http://www.dasninternational.org/";
                break;

        }

        Intent toWebview=new Intent(getApplicationContext(), WebViewConnectPeople.class);
        toWebview.putExtra("link", url );
        startActivity(toWebview);
    }

}
