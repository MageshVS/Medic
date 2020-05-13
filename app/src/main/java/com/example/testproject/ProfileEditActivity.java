package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class ProfileEditActivity extends AppCompatActivity {

    private TextView newName;
    private TextView newAge;
    private TextView newHeight;
    private TextView newWeight;
    private TextView newPhone;
    private TextView newDob;
    private TextView newHomeTown;
    private TextView newgender;
    private TextView newBlood;
    private  Firebase mref;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String user = currentuser.getUid();
        String userName = currentuser.getEmail();
        //String path
        mref = new Firebase("https://testproject-15dd6.firebaseio.com/Appointment/" + user +"/Profile");

        try {
            mref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, String> map = dataSnapshot.getValue(Map.class);
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
                    final Runnable r = new Runnable(){
                        public void run(){
                           // startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            //finish();
                        }
                    };
                    new Handler().postDelayed(r,10000);
                    String fname = map.get("name");
                    String fage = map.get("age");
                    String fheight = map.get("height");
                    String fweight = map.get("weight");
                    String fphone = map.get("phone");
                    String fdob = map.get("date Of Birth");
                    String fhome_town = map.get("hometown");
                    String fgender = map.get("gender");
                    String fblood_group = map.get("blood");
                    newName.setText(fname);
                    newAge.setText(fage);
                    newHeight.setText(fheight);
                    newWeight.setText(fweight);
                    newPhone.setText(fphone);
                    newDob.setText(fdob);
                    newHomeTown.setText(fhome_town);
                    newgender.setText(fgender);
                    newBlood.setText(fblood_group);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }catch (Exception nul){
            Toast.makeText(getApplicationContext(),"Error"+nul, Toast.LENGTH_LONG).show();
        }
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newName = (TextView)findViewById(R.id.profileViewName);
        newAge = (TextView)findViewById(R.id.profileViewAge);
        newHeight = (TextView)findViewById(R.id.profileViewHeight);
        newWeight = (TextView)findViewById(R.id.profileViewWeight);
        newPhone = (TextView)findViewById(R.id.profileViewPhone);
        newDob = (TextView)findViewById(R.id.profileViewDoB);
        newHomeTown = (TextView)findViewById(R.id.profileViewHomeTown);
        newgender = (TextView)findViewById(R.id.profileViewGender);
        newBlood = (TextView)findViewById(R.id.profileViewBlood);

        Button button = (Button)findViewById(R.id.profile_edit_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
             name = data.getStringExtra("name");
            String age = data.getStringExtra("age");
            String height = data.getStringExtra("height");
            String weight = data.getStringExtra("weight");
            String phone = data.getStringExtra("phone");
            String dob = data.getStringExtra("dob");
            String hometown = data.getStringExtra("home_town");

            newName.setText(name);

        }
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
