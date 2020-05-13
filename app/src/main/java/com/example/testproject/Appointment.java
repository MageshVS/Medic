package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class Appointment extends AppCompatActivity {
    private ImageView mDisplay;
    private ImageView mTime;
    private TextView datedisplay;
    private TextView timedisplay;
    private Editable problem;
    private String format = "";
    public Editable name;
    public CharSequence date;
    public CharSequence time;
    private EditText phone;
    private String user;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Firebase mref;


    private static final int PERMISSION_REQUEST_CODE = 1;
    private Button sendSMS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        Firebase.setAndroidContext(this);

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        user = currentuser.getUid();
        //String path
        mref = new Firebase("https://testproject-15dd6.firebaseio.com/Appointment/" + user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Appointment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Appointment Page");
        final TextView t1 = (TextView) findViewById(R.id.textView);
        datedisplay = (TextView) findViewById(R.id.dateDisplay);
        mDisplay = (ImageView) findViewById(R.id.dateIcon);
        mDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Appointment.this, R.style.Theme_AppCompat_Dialog, mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getColor(android.R.color.transparent)));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Log.d(TAG, "onsetdate date:" +i+"/"+i1+"/"+"i2");
                month = month + 1;
                String date = day + "/ " + month + "/ " + year;
                datedisplay.setText(date);
            }
        }

        ;
        mTime = (ImageView) findViewById(R.id.timeIcon);
        timedisplay = (TextView)findViewById(R.id.timeDisplay);
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(Appointment.this,
                        R.style.Theme_AppCompat_Dialog, timeSetListener, hour, minute, false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getColor(android.R.color.transparent)));
                dialog.show();

            }
        });
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if (hour == 0) {
                    hour += 12;
                    format = "AM";
                } else if (hour == 12) {
                    format = "PM";
                } else if (hour > 12) {
                    hour -= 12;
                    format = "PM";
                } else {
                    format = "AM";
                }
                String time = hour + " : " + minute + " " + format;
                timedisplay.setText(time);
            }
        };
        final Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submit.setVisibility(View.GONE);


                EditText nameBox = (EditText) findViewById(R.id.nameView);
                name = nameBox.getText();

                TextView dateBox = (TextView) findViewById(R.id.dateDisplay);
                date = dateBox.getText();

                TextView timeBox = (TextView) findViewById(R.id.timeDisplay);
                time = timeBox.getText();

                EditText problemBox = (EditText) findViewById(R.id.problemDisplay);
                problem = problemBox.getText();

                final EditText phoneNumber = (EditText) findViewById(R.id.phoneView);

                if (!name.toString().isEmpty() && !date.toString().isEmpty() &&
                        !problem.toString().isEmpty() && !phoneNumber.getText().toString().isEmpty() && phoneNumber.length() == 10 &&
                        !time.toString().isEmpty()) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (checkPermission()) {
                            Log.e("permission", "Permission already granted.");
                        } else {
                            requestPermission();
                        }
                    }
                    final EditText smsText = (EditText) findViewById(R.id.problemDisplay);
                    String smsMessage = "Confirmation Message ! \n" + name.toString() + "  , Your Appointment Has Been Fixed on " + date.toString() +
                            "  -  " + time.toString() + "    - TEAM MEDIC ";
                    String sms = smsText.getText().toString();
                    String phoneNum = phoneNumber.getText().toString();
                    if (!TextUtils.isEmpty(smsMessage) && !TextUtils.isEmpty(phoneNum)) {
                        if (checkPermission()) {
                            //Get the default SmsManager//
                            SmsManager smsManager = SmsManager.getDefault();
                            //Send the SMS//
                            smsManager.sendTextMessage(phoneNum, null, smsMessage, null, null);
                        } else {
                            Toast.makeText(Appointment.this, "Permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    String mname = name.toString();
                    String mdate = date.toString();
                    String mtime = time.toString();
                    String mproblem = problem.toString();
                    Firebase key = mref.child(mname);
                    //key.setValue(user);
                    //Firebase keyChild = key.child(mname);
                    //keyChild.setValue(mname);
                    Firebase nameChild = key.child("name");
                    nameChild.setValue(mname);
                    Firebase dateChild = key.child("date");
                    dateChild.setValue(mdate);
                    Firebase timeChild = key.child("time");
                    timeChild.setValue(mtime);
                    Firebase problemChild = key.child("problem");
                    problemChild.setValue(mproblem);

                    Bundle bundle = new Bundle();
                    bundle.putString("name", String.valueOf(name));
                    bundle.putString("date", String.valueOf(date));
                    bundle.putString("time", String.valueOf(time));
                    bundle.putString("doctor", "Doctor 1");
                    Fragment fragment = new Appointment_Result();
                    fragment.setArguments(bundle);
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.page1, fragment).addToBackStack(null);
                    transaction.commit();
                } else {
                    Toast.makeText(Appointment.this, "Some Fields is Empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toollog:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Appointment.this, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(Appointment.this,
                            "Permission accepted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Appointment.this,
                            "Permission denied", Toast.LENGTH_LONG).show();
                    Button sendSMS = (Button) findViewById(R.id.submit);
                    sendSMS.setEnabled(false);

                }
                break;
        }
    }
}

