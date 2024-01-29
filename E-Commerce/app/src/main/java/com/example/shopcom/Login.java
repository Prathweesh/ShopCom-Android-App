package com.example.shopcom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shopcom.databinding.ActivityLoginBinding;


public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    MyDatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDB = new MyDatabaseHelper(this);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.loginName.getText().toString();
                String password = binding.loginPassword.getText().toString();
                if(name.equals("")||password.equals(""))
                    Toast.makeText(Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = myDB.checkNamePassword(name, password);
                    if(checkCredentials == true){
                        String userName = name;
                        Toast.makeText(Login.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Name", userName);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}