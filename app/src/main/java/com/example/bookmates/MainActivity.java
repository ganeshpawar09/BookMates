package com.example.bookmates;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ImageView actionButton;
    BottomNavigationView navigationView;
    Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionButton= findViewById(R.id.main_activity_action_image);
        navigationView=findViewById(R.id.main_activity_bottomNavigationView);
        toolbar=findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        SharedPreferences preferences = getSharedPreferences("LoginAndLogout", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        boolean isLoggedIn = preferences.getBoolean("login", false); // Get the boolean value from preferences

                        if (isLoggedIn) {
                            editor.putBoolean("login", false); // Set the boolean value to false
                            editor.apply(); // Save the changes to the preferences
                        }
                        startActivity(new Intent(MainActivity.this,_1_Splash_Screen.class));

                        Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                int id=item.getItemId();
                if(id==R.id.books)
                {

                    ft.replace(R.id.main_activity_frameLayout,new _4_Book_Fragment());
                    ft.commit();
                }
                else if(id==R.id.building)
                {
                    ft.replace(R.id.main_activity_frameLayout,new _5_Flat_Fragment());
                    ft.commit();
                }
                return true;
            }
        });
        navigationView.setSelectedItemId(R.id.books);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,_10_Add_New_Request.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}