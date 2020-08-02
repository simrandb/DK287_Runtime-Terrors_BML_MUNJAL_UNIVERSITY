package com.example.alzheimers_detection;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class NearbyDoctors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_doctors);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        LocationAPI location = new LocationAPI(NearbyDoctors.this);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            String text[] = location.getLocation().trim().split(" ");
            String city =city = text[0].trim();
            WebView w = findViewById(R.id.web);

            String link = null;
            if (city != null && !city.equals("")) {

                String doctor = "neurologist";
                link = "https://www.practo.com/search?results_type=doctor&q=%5B%7B%22word%22%3A%22"
                        + doctor + "%22%2C%22autocompleted%22%3Atrue%2C%22category%22%3A%22subspeciality%22%7D%5D&city=" + city;

            }else{
                link = "https://www.practo.com/";
            }
            w.loadUrl(link);
            w.getSettings().setJavaScriptEnabled(true);
            w.setWebViewClient(new WebViewClient());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}