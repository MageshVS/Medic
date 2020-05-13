package com.example.testproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class Appointment_Result extends Fragment {

    public Appointment_Result() {
        // Required empty public constructor
    }

    private Button logout;
    private Firebase mref;
    private FirebaseAuth.AuthStateListener mauthlistener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Firebase.setAndroidContext(Appointment_Result);
        mref = new Firebase("https://testproject-15dd6.firebaseio.com/Appointment");



        Bundle bundle = this.getArguments();
        String nameText = bundle.getString("name");
        String dateText = bundle.getString("date");
        String timeText = bundle.getString("time");

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fragment1, container, false);
        final TextView name = (TextView)v.findViewById(R.id.name);
        name.setText("Hello ! "+nameText+" Your Appointment Has Been Set On "+dateText+" "+timeText+" ! ");



        logout = (Button)v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));

            }
        });
        mauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        };
        return  v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mauthlistener);
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(mauthlistener);
    }
}
