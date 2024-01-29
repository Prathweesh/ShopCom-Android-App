package com.example.shopcom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shopcom.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    MyDatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDB = new MyDatabaseHelper(this);
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.signupName.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();
                String phoneno = binding.signupPhoneNo.getText().toString();
                String address = binding.signupAddress.getText().toString();
                String email = binding.signupEmail.getText().toString();

                if(name.equals("")||password.equals("")||confirmPassword.equals(""))
                    Toast.makeText(Signup.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(confirmPassword)){
                        Boolean checkUserName = myDB.checkName(name);
                        if(checkUserName == false){
                            Boolean insert = myDB.insertData(name,password,email,phoneno,address);
                            if(insert == true){
                                Toast.makeText(Signup.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Signup.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Signup.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Signup.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
    }
}