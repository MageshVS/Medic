package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout view;
    private RelativeLayout emergency;
    private RelativeLayout reportView;
    private RelativeLayout bloodBankView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        reportView = (RelativeLayout)findViewById(R.id.list2);
        reportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReportActivity.class));
                finish();
            }
        });

        view = (RelativeLayout)findViewById(R.id.list1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DoctorsActivity.class));
            }
        });

        emergency = (RelativeLayout)findViewById(R.id.list4);
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EmergencyActivity.class));
            }
        });

        bloodBankView = (RelativeLayout)findViewById(R.id.list3);
        bloodBankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BloodBankActivity.class));
            }
        });

    }


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2 , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.toollog:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;

            case R.id.toolprofile:
                FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
                String user = currentuser.getUid();
                String userName = currentuser.getEmail();
                //String path
                Firebase mref = new Firebase("https://testproject-15dd6.firebaseio.com/Appointment/" + user +"/Profile");
                Firebase fbname = mref.child("Name");
                fbname.setValue("");
                Firebase fbage = mref.child("Age");
                fbage.setValue("");
                Firebase fbheight = mref.child("Height");
                fbheight.setValue("");
                Firebase fbweight = mref.child("Weight");
                fbweight.setValue("");
                Firebase fbphone = mref.child("Phone");
                fbphone.setValue("");
                Firebase fbdob = mref.child("Date Of Birth");
                fbdob.setValue("");
                Firebase fbhome_towm = mref.child("Hometown");
                fbhome_towm.setValue("");
                Firebase fbgender = mref.child("Gender");
                fbgender.setValue("");
                Firebase fbblood = mref.child("Blood");
                fbblood.setValue("");
                startActivity(new Intent(getApplicationContext(), ProfileEditActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
