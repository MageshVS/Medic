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


public class RegisterActivity extends AppCompatActivity {

    private Firebase link;
    private  EditText name;
    private EditText email;
    private EditText password;
    private Button btn;
    private TextView down;
    private TextView nameError;
    private TextView mailError;
    private TextView passwordError;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        Firebase.setAndroidContext(this);
        mauth = FirebaseAuth.getInstance();
        link = new Firebase("https://testproject-15dd6.firebaseio.com/Users");

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btn = (Button) findViewById(R.id.button);

        down = (TextView)findViewById(R.id.goo);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        mauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthlistener);
    }

    private void startSignUp() {

        String mname = name.getText().toString();
        String mail = email.getText().toString();
        String pwd = password.getText().toString();
        nameError = (TextView)findViewById(R.id.emailError);
        mailError = (TextView)findViewById(R.id.mailError);
        passwordError = (TextView)findViewById(R.id.passwordError);

        if (TextUtils.isEmpty(mname)) {
            nameError.setVisibility(View.VISIBLE);
            mailError.setVisibility(View.INVISIBLE);
            passwordError.setVisibility(View.INVISIBLE);
            nameError.setText("Name Field is Empty");
        }

        else if (TextUtils.isEmpty(mail)) {
            nameError.setVisibility(View.INVISIBLE);
            passwordError.setVisibility(View.INVISIBLE);
            mailError.setVisibility(View.VISIBLE);
            mailError.setText("Invalid Email Address");
        }
        else if (TextUtils.isEmpty(pwd) || pwd.length()<8) {
            nameError.setVisibility(View.INVISIBLE);
            mailError.setVisibility(View.INVISIBLE);
            passwordError.setVisibility(View.VISIBLE);
            passwordError.setText("Password Should Be Above 8 Character");
        }
        else {
            nameError.setVisibility(View.INVISIBLE);
            mailError.setVisibility(View.INVISIBLE);
            passwordError.setVisibility(View.INVISIBLE);
            mauth.createUserWithEmailAndPassword(mail, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "SignIn Fsiled", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }
}
