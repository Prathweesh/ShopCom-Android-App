package com.example.shopcom;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    Context context;

    MyDatabaseHelper myDB;
    CardView c1,c2,c3,c4,c5,c6,c7;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        myDB = new MyDatabaseHelper(context);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        myDB.insertData();

        c1=view.findViewById(R.id.all);
        c2=view.findViewById(R.id.clothing);
        c3=view.findViewById(R.id.electronics);
        c4=view.findViewById(R.id.home);
        c5=view.findViewById(R.id.medicine);
        c6=view.findViewById(R.id.fitness);
        c7=view.findViewById(R.id.footwear);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, product_category.class);
                intent.putExtra("Category", "all products");
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, product_category.class);
                intent.putExtra("Category", "clothing");
                startActivity(intent);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, product_category.class);
                intent.putExtra("Category", "electronics");
                startActivity(intent);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, product_category.class);
                intent.putExtra("Category", "home");
                startActivity(intent);
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, product_category.class);
                intent.putExtra("Category", "medicine");
                startActivity(intent);
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, product_category.class);
                intent.putExtra("Category", "fitness");
                startActivity(intent);
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, product_category.class);
                intent.putExtra("Category", "footwear");
                startActivity(intent);
            }
        });
        return view;
    }
}
