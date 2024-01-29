package com.example.shopcom;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileFragment extends Fragment {
    TextView t1;
    Context context;
    MyDatabaseHelper myDB;
    TextView nameTextView, emailTextView, phoneTextView, addressTextView;
    String username,email,phone,address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        context = container.getContext();
        myDB = new MyDatabaseHelper(context);
        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);
        phoneTextView = view.findViewById(R.id.phone);
        addressTextView = view.findViewById(R.id.address);
        String name = SharedData.sharedString;
        displayDetails(name);
        return view;
    }
    void displayDetails(String username) {
        Cursor cursor = myDB.readUserData(username);

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No data available.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                username = cursor.getString(0);
                nameTextView.setText("Username: "+username);

                email= cursor.getString(1);
                emailTextView.setText("Email : "+email);

                phone = cursor.getString(2);
                phoneTextView.setText("Phone Number : "+phone);

                address = cursor.getString(3);
                addressTextView.setText("Address : "+address);
            }
        }
        cursor.close();
    }
}