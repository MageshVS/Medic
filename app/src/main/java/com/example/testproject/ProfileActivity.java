package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    String name;
    String age;
    String height;
    String weight;
    String phone;
    String dob;
    String home_town;
    String gender;
    String blood_group;
    private TextView profileName;
    private TextView profileAge;
    private TextView profileHeight;
    private TextView profileWeight;
    private TextView profilePhone;
    private TextView profileDob;
    private TextView profileHomeTown;
    private TextView profileGender;
    private TextView profileBlood;
    private Firebase mref;
    //String ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String user = currentuser.getUid();
        String userName = currentuser.getEmail();
        //String path
        mref = new Firebase("https://testproject-15dd6.firebaseio.com/Appointment/" + user +"/Profile");


        
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner spinner = (Spinner)findViewById(R.id.bloodContainer);
        String[] items = new String[]{"Blood Group","A+","A-","B+","B-","O+","O-","AB+","AB-"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0) {
                   // Toast.makeText(getApplicationContext(), (String) adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                    blood_group = (String) adapterView.getItemAtPosition(i);
                }
                else{
                   // Toast.makeText(getApplicationContext(),"Choose a Blood Group",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"Choose a Blood Group",Toast.LENGTH_SHORT).show();
            }
        });

        profileName = (TextView)findViewById(R.id.nameProfile);
        profileAge = (TextView)findViewById(R.id.ageProfile);
        profileHeight = (TextView)findViewById(R.id.heightProfile);
        profileWeight = (TextView)findViewById(R.id.weightProfile);
        profilePhone = (TextView)findViewById(R.id.phoneProfile);
        profileDob = (TextView)findViewById(R.id.dateProfile);
        profileHomeTown = (TextView)findViewById(R.id.hometownProfile);

        Button button = (Button)findViewById(R.id.profile_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = profileName.getText().toString();
                age = profileAge.getText().toString();
                height = profileHeight.getText().toString();
                weight = profileWeight.getText().toString();
                phone = profilePhone.getText().toString();
                dob = profileDob.getText().toString();
                home_town = profileHomeTown.getText().toString();

                //Firebase Uploading
                //Firebase key = mref.child(name);
                Firebase fname = mref.child("name");
                fname.setValue(name);
                Firebase fage = mref.child("age");
                fage.setValue(age);
                Firebase fheight = mref.child("height");
                fheight.setValue(height);
                Firebase fweight = mref.child("weight");
                fweight.setValue(weight);
                Firebase fphone = mref.child("phone");
                fphone.setValue(phone);
                Firebase fdob = mref.child("date Of Birth");
                fdob.setValue(dob);
                Firebase fhome_towm = mref.child("hometown");
                fhome_towm.setValue(home_town);
                Firebase fgender = mref.child("gender");
                fgender.setValue(gender);
                Firebase fblood = mref.child("blood");
                fblood.setValue(blood_group);
                Intent intent = new Intent(getApplicationContext(), ProfileEditActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("phone", phone);
                intent.putExtra("dob", dob);
                intent.putExtra("home_town", home_town);

                setResult(2,intent);
                finish();
            }
        });

        try {
            mref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, String> map = dataSnapshot.getValue(Map.class);
                    String fname = map.get("name");
                    String fage = map.get("age");
                    String fheight = map.get("height");
                    String fweight = map.get("weight");
                    String fphone = map.get("phone");
                    String fdob = map.get("date Of Birth");
                    String fhome_town = map.get("hometown");
                    String fgender = map.get("gender");
                    String fblood = map.get("blood");
                    profileName.setText(fname);
                    profileAge.setText(fage);
                    profileHeight.setText(fheight);
                    profileWeight.setText(fweight);
                    profilePhone.setText(fphone);
                    profileDob.setText(fdob);
                    profileHomeTown.setText(fhome_town);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }catch (NullPointerException ex){
            Toast.makeText(getApplicationContext(),"Error"+ex, Toast.LENGTH_LONG).show();
        }
    }
    public  void radioOption(View view){
        Boolean clicked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radioMale:
                if (clicked)
                    gender = "Male";
                   // Toast.makeText(getApplicationContext(),gender,Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioFemale:
               if (clicked)
                   gender = "Female";
                   // Toast.makeText(getApplicationContext(),gender,Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioOthers:
                if (clicked)
                    gender = "Others";
                   // Toast.makeText(getApplicationContext(),gender,Toast.LENGTH_SHORT).show();
                break;
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
