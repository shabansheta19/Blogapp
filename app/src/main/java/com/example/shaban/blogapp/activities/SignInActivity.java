package com.example.shaban.blogapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shaban.blogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private Button createAccountButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = (EditText)findViewById(R.id.emailSignInEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordSignInEditText);
        signInButton = (Button)findViewById(R.id.signInBtn);
        createAccountButton = (Button)findViewById(R.id.createAccountBtn);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out
                }
            }
        };

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(emailEditText.getText().toString()) && !TextUtils.isEmpty(passwordEditText.getText().toString())) {
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    login(email, password);
                } else {
                    Toast.makeText(SignInActivity.this, "fill the required information.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, "sign in failed.", Toast.LENGTH_SHORT).show();
                    } else {
                        //success
                        Intent intent = new Intent(SignInActivity.this,BlogActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
