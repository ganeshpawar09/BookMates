package com.example.bookmates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class _2_Send_OTP extends AppCompatActivity {
    EditText mobile_number;
    FloatingActionButton actionButton;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_send_otp);
        mobile_number=findViewById(R.id.mobile_number);
        actionButton=findViewById(R.id.send_otp_action_button);
        mobile_number.requestFocus();



        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number=mobile_number.getText().toString().trim();
                if(number.length()==10)
                {
                    Intent intent=new Intent(_2_Send_OTP.this,_3_OTP_Verification.class);

                    intent.putExtra("number",number);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}