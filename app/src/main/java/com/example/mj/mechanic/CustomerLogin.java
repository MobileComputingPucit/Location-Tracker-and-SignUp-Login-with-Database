package com.example.mj.mechanic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerLogin extends AppCompatActivity implements View.OnClickListener{

    private EditText email,password;
    private Button login,register;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.buttonlogin);
        register=(Button)findViewById(R.id.buttonregister);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        auth=FirebaseAuth.getInstance();
    }

    public void registerUser() {
        String uname = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (uname.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(uname).matches()) {
            email.setError("Invalid email address");
            email.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if (pass.length() < 6) {
            password.setError("Minimum length of password should be 6!");
            password.requestFocus();
            return;
        }
        auth.createUserWithEmailAndPassword(uname, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                    String user_id =auth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Customers").child(user_id);
                    current_user_db.setValue(true);
                }
                else
                {
                    if(task.getException()instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "User is already registered", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void loginUser() {
        String uname = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (uname.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(uname).matches()) {
            email.setError("Invalid email address");
            email.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if (pass.length() < 6) {
            password.setError("Minimum length of password should be 6!");
            password.requestFocus();
            return;
        }
        auth.signInWithEmailAndPassword(uname, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent=new Intent(CustomerLogin.this,CustomerMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.buttonregister:
                registerUser();
                break;
            case R.id.buttonlogin:
                loginUser();
        }
    }
}

