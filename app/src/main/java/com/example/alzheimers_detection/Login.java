package com.example.alzheimers_detection;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class Login extends AppCompatActivity implements View.OnClickListener{
    ImageView tootpimage,loginimage;
    String urlloginimage;
    TextInputLayout hidepassword;
    Switch rememberme;
    DatabaseReference dbUsers;
    EditText loginusername;
    int flag=0;
    String email,password,verificationId;
    EditText loginphonepassword;
    TextView forgotp;
    Button login;
    TextView signup;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        askPermissions();
        FirebaseApp.initializeApp(this);
        hidepassword=findViewById(R.id.hidepassword);
        mAuth = FirebaseAuth.getInstance();
        loginimage=findViewById(R.id.loginimage);
        urlloginimage="https://firebasestorage.googleapis.com/v0/b/alzheimers-detection.appspot.com/o/body.jpeg?alt=media&token=a4407877-110d-48d9-b42f-9f424c9b5028";
        Picasso.with(this).load(urlloginimage).into(loginimage);
        tootpimage=findViewById(R.id.tootpimage);
        loginusername=(EditText) findViewById(R.id.loginusername);
        loginphonepassword=(EditText)findViewById(R.id.loginphonepassword);
        forgotp=findViewById(R.id.forgotp);
        login=(Button)findViewById(R.id.login);
        signup=findViewById(R.id.signup);

        loginusername.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String MobilePattern = "[0-9]+";
                if (loginusername.getText().toString().trim().matches(MobilePattern))
                {
                    tootpimage.setAlpha(0);
                    hidepassword.setVisibility(View.INVISIBLE);
                    forgotp.setAlpha(0);
                    flag=1;
                }
                else
                {
                    forgotp.setAlpha(1);
                    tootpimage.setAlpha(255);
                    hidepassword.setVisibility(View.VISIBLE);
                    flag=0;
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0) {
                    loginToAccount();
                }
                else
                {
                    loginToAccountbyPhone();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Signup.class);
                startActivity(i);




            }
        });




        forgotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(i);
            }
        });

        TextView selectlang = findViewById(R.id.changelang);
        selectlang.setOnClickListener(this);

    }

    public  void loginToAccountbyPhone()
    {
        String MobilePattern = "[0-9]{10}";
        if(!(loginusername.getText().toString().trim().matches(MobilePattern)))
        {
            loginusername.requestFocus();
            loginusername.setError("Please enter valid phone number!");
        }
        else
        {
            Intent i=new Intent(getApplicationContext(),LoginOtpByPhone.class);
            i.putExtra("tophonenumber",""+loginusername.getText().toString().trim());
            startActivity(i);
        }
    }

    public void loginToAccount()
    {
        email=loginusername.getText().toString().trim();
        password=loginphonepassword.getText().toString().trim();
        if(email.isEmpty())
        {
            loginusername.setError("Email id is required!");
            loginusername.requestFocus();
        }
        else if(password.isEmpty())
        {
            loginphonepassword.setError("Password is required!");
            loginphonepassword.requestFocus();
        }
        else if(password.isEmpty() && email.isEmpty())
        {
            loginphonepassword.setError("Password is required!");
            loginphonepassword.requestFocus();
            loginusername.setError("Email id is required!");
            loginusername.requestFocus();
        }
        else if(!password.isEmpty() && !email.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        private static final String TAG = "Login";
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(mAuth.getCurrentUser().isEmailVerified()) {

                                    UserSharedPreferences userSharedPreferences=new UserSharedPreferences(getApplicationContext());
                                    userSharedPreferences.setUsername(loginusername.getText().toString().trim());
                                    nextActivity();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Please check your email for verification!",Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "No such User exists. Please check username and password.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void nextActivity()
    {
        Intent ii=new Intent(getApplicationContext(),HomeScreen.class);
        startActivity(ii);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestRecordAudioPermission() {

        String requiredPermission = Manifest.permission.RECORD_AUDIO;

        // If the user previously denied this permission then show a message explaining why
        // this permission is needed
        if (getApplicationContext().checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_GRANTED) {

        } else {

            Toast.makeText(getApplicationContext(), "This app needs to record audio through the microphone....", Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{requiredPermission}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == 101 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // This method is called when the  permissions are given
        }
    }

    private void askPermissions(){
        String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.RECORD_AUDIO};
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, permissions, 101);
        }
    }

    @Override
    public void onClick(View v) {
        Resources res;
        res = getResources();
        String[] languages =res.getStringArray(R.array.languages_array);

        LayoutInflater layoutInflater =
                (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.orientation_popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        Button cancel = (Button)popupView.findViewById(R.id.cancel);
        TextView tv = popupView.findViewById(R.id.popuptv);
        tv.setText(" -- Choose a language -- ");
        final Spinner popupSpinner = (Spinner)popupView.findViewById(R.id.daysSpinner);
        /*ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Login.this,
                        android.R.layout.simple_spinner_item, languages);*/
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Login.this,
                        android.R.layout.select_dialog_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        popupSpinner.setAdapter(adapter);
        popupView.findViewById(R.id.okay).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                setcode(popupSpinner.getSelectedItem().toString().toLowerCase());
                popupWindow.dismiss();
            }});

        cancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }});
        //popupWindow.showAsDropDown(dayTv, 50, -30);
        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
    }

    void setcode(String lang){
        String code = "";
        switch(lang){
            case "english" : code ="en";
                break;

            case "hindi" : code = "hi";
                break;

            default: code ="en";
            break;
        }

        Localisation local = new Localisation(this);
        local.changelanguage(code);
    }

}

