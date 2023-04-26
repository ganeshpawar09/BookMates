package com.example.bookmates;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class _1_Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1_splash_screen);

        SharedPreferences pref=getSharedPreferences("LoginAndLogout",MODE_PRIVATE);
        boolean login=pref.getBoolean("login", false);
        if(login)
        {
            _4_Book_Fragment.book();
            _5_Flat_Fragment.flat();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(login) {

                    startActivity(new Intent(_1_Splash_Screen.this, MainActivity.class));
                }
                else
                    startActivity(new Intent(_1_Splash_Screen.this, _2_Send_OTP.class));
            }
        },2000);

    }

}