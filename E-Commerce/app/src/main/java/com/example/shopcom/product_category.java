package com.example.shopcom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class product_category extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    MyDatabaseHelper myDB;
    ArrayList<String> name, description, price, category,image;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        myDB = new MyDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerview);
        name = new ArrayList<>();
        description = new ArrayList<>();
        price = new ArrayList<>();
        category = new ArrayList<>();
        image = new ArrayList<>();
        t1=findViewById(R.id.textView);
        Bundle bundle = getIntent().getExtras();
        String cat = bundle.getString("Category");
        t1.setText(bundle.getString("Category").toUpperCase());
        storeDataInArrays(cat);
        customAdapter = new CustomAdapter(this, name, description, price, image);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //myDB.deleteData();
    }
    void storeDataInArrays(String cat) {
        Cursor cursor = myDB.readAllData(cat);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data available.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(0));
                price.add(cursor.getString(1));
                description.add(cursor.getString(2));
                image.add(cursor.getString(4));
            }
        }

        cursor.close();
    }
}