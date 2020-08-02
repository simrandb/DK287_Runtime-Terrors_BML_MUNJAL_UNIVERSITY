package com.example.alzheimers_detection;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPreferences {
    Context context;
    SharedPreferences sharedPreferences;
    String username;


    public  void removeUser()
    {
        sharedPreferences.edit().clear().commit();
    }


    public String getUsername() {
        username=sharedPreferences.getString("userdata","");
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        sharedPreferences.edit().putString("userdata",username).commit();
    }

    public UserSharedPreferences(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
    }

}

/*
Code for when Splash screen is about to end(in the video it was shown with a timer)

declare UserSharedPreferences obj first
final UserSharedPreferences user=new UserSharedPreferences(getApplicationContext());




method:

if(user.getUsername!="")
{
    Intent i=new Intent(getApplicationContext(),HomeScreen.class);
    i.putExtra("username",user.getUsername());
    startActivity(i);
    finish();
}

else
{
    Intent i=new Intent(getApplicationContext(),Login.class);
    startActivity(i);
    finish();
}
 */


//Code to logout after clicking on logout button(logout button probably on HomeScreen)
/*


new UserSharedPreferences(getApplicationContext()).removeUser();
Intent i=new Intent(getApplicationContext(),Login.class);
    startActivity(i);
    finish();
 */