package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        final Runnable r = new Runnable(){
           public void run(){
               startActivity(new Intent(getApplicationContext(), LoginActivity.class));
               finish();
           }
        };
        new Handler().postDelayed(r,500);


    }
}
