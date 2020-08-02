package com.example.alzheimers_detection;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewConnectPeople extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_connectpeople);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        String link;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                link= null;     //to remain on a safe side, direct it to some other link - to Shuchi
            } else {
                link= extras.getString("link");
            }
        } else {
            link= (String) savedInstanceState.getSerializable("link");
        }

        WebView w = findViewById(R.id.webview);
        w.loadUrl(link);
        w.getSettings().setJavaScriptEnabled(true);
        w.setWebViewClient(new WebViewClient());

    }


}

