package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private Firebase link;
    private EditText email;
    private EditText password;
    private TextView emailError;
    private TextView passwordError;
    private Button btn;
    private TextView down;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        Firebase.setAndroidContext(this);
        mauth = FirebaseAuth.getInstance();
        link = new Firebase("https://testproject-15dd6.firebaseio.com/Users");

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btn = (Button) findViewById(R.id.button);


        mauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (email.getText().toString().equals("admin@mail.com") && password.getText().toString().equals("testpass")){
                    startActivity(new Intent(getApplicationContext(), AdminHomeActivity.class));
                    finish();
                }
            }
        };

        down = (TextView)findViewById(R.id.go);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        mauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthlistener);
    }

    private void startSignIn() {
        String mail = email.getText().toString();
        String pwd = password.getText().toString();

        emailError = (TextView)findViewById(R.id.emailError);
        passwordError = (TextView)findViewById(R.id.passwordError);

        if (TextUtils.isEmpty(mail) ) {
            emailError.setVisibility(View.VISIBLE);
            passwordError.setVisibility(View.INVISIBLE);
            emailError.setText("Invalid Email Address");
        }
        else if(TextUtils.isEmpty(pwd)){
            emailError.setVisibility(View.INVISIBLE);
            passwordError.setVisibility(View.VISIBLE);
            passwordError.setText("Invalid Password");
        }
        else {
            emailError.setVisibility(View.INVISIBLE);
            passwordError.setVisibility(View.INVISIBLE);
            mauth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "SignIn Problem", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
