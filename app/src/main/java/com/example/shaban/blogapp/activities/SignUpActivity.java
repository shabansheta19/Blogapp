package com.example.shaban.blogapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shaban.blogapp.R;
import com.example.shaban.blogapp.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText fNameEditText;
    private EditText lNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signUpButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fNameEditText = (EditText)findViewById(R.id.fNameSignUpEditText);
        lNameEditText = (EditText)findViewById(R.id.lNameSignUpEditText);
        emailEditText = (EditText)findViewById(R.id.emailSignUpEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordSignUpEditText);
        signUpButton = (Button)findViewById(R.id.SignUpBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(fNameEditText.getText().toString()) && !TextUtils.isEmpty(lNameEditText.getText().toString())
                        && !TextUtils.isEmpty(emailEditText.getText().toString()) && !TextUtils.isEmpty(passwordEditText.getText().toString())) {
                    String fName = fNameEditText.getText().toString();
                    String lName = lNameEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    signUp(fName, lName, email, password);
                }
            }
        });
    }

    private void signUp(final String fName, final String lName, final String email, final String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //User user = new User(fName,lName,email,password);
                        //databaseReference = firebaseDatabase.getReference().child("users");
                        //databaseReference.setValue(user);
                        startActivity(new Intent(SignUpActivity.this,BlogActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this,"sign up failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
