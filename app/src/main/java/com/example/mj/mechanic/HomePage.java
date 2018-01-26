package com.example.mj.mechanic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    private Button mechanic,customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mechanic=(Button)findViewById(R.id.mechanic);
        customer=(Button)findViewById(R.id.customer);
        mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
        );
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,CustomerLogin.class);
                startActivity(intent);
                finish();
                return;
            }
        }
        );
    }

}
