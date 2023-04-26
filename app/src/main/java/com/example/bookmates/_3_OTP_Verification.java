package com.example.bookmates;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class _3_OTP_Verification extends AppCompatActivity {
    TextView add_mobile_number;
    FloatingActionButton actionButton;
    EditText otp;
    String number, mVerificationId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3_otp_verification);
        add_mobile_number=findViewById(R.id.add_mobile_number);
        actionButton=findViewById(R.id.otp_verification_action_button);
        otp=findViewById(R.id.otp);

        otp.requestFocus();

        number=getIntent().getStringExtra("number");
        add_mobile_number.setText(add_mobile_number.getText()+" "+number);

        mAuth = FirebaseAuth.getInstance();

        number="+91"+number;

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String verificationCode = otp.getText().toString().trim();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);

            }
        });


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(_3_OTP_Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificationId, forceResendingToken);
            mVerificationId = verificationId;
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(_3_OTP_Verification.this, "Verification successful!", Toast.LENGTH_SHORT).show();

                            SharedPreferences pref=getSharedPreferences("LoginAndLogout",MODE_PRIVATE);
                            SharedPreferences.Editor editor= pref.edit();
                            editor.putString("mobile_number", getIntent().getStringExtra("number"));


                            boolean isLoggedIn = pref.getBoolean("login", false); // Get the boolean value from preferences
                            if (!isLoggedIn) {
                                editor.putBoolean("login", true); // Set the boolean value to true
                            }
                            editor.apply(); // Save the changes to the preferences

                            _4_Book_Fragment.book();
                            _5_Flat_Fragment.flat();

                            Intent intent = new Intent(_3_OTP_Verification.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(_3_OTP_Verification.this, "Verification failed!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


}



