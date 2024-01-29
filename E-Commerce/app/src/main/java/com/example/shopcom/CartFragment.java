package com.example.shopcom;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    Context context;
    RecyclerView recyclerView;
    CustomAdapterCart customAdapter;
    MyDatabaseHelper myDB;
    TextView t1;
    Button check;
    ArrayList<String> name, description, price, category,image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        myDB = new MyDatabaseHelper(context);
        recyclerView = view.findViewById(R.id.recyclerview);
        check = view.findViewById(R.id.checkout);
        name = new ArrayList<>();
        description = new ArrayList<>();
        price = new ArrayList<>();
        category = new ArrayList<>();
        image = new ArrayList<>();
        t1 = view.findViewById(R.id.totalPrice);
        String username = SharedData.sharedString;
        storeDataInArrays(username);
        customAdapter = new CustomAdapterCart(context, name, description, price, image,t1);
        int sum = 0;

        for (String priceStr : price) {
            int priceInt = Integer.parseInt(priceStr);
            sum += priceInt;
        }

        String sumStr = String.valueOf(sum);
        t1.setText("Final Price = ₹"+sumStr);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //myDB.deleteData();
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.moveItemsFromCartToOrders(username);

                LayoutInflater inflater = getLayoutInflater();
                View customToastView = inflater.inflate(R.layout.orderconfirmation, null);
                Toast customToast = new Toast(context);
                customToast.setDuration(Toast.LENGTH_SHORT);
                customToast.setView(customToastView);
                customToast.show();

                name.clear();
                price.clear();
                description.clear();
                image.clear();
                myDB.clearCart(username);
                t1.setText("Final Price = ₹0");
                customAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
    void storeDataInArrays(String username) {
        Cursor cursor = myDB.readCartData(username);

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No Items in Cart.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(0)); // Assuming column1 contains names
                price.add(cursor.getString(2)); // Assuming column1 contains prices
                description.add(cursor.getString(3)); // Assuming column1 contains descriptions
                image.add(cursor.getString(4)); // Assuming column2 contains image URLs
            }
        }
        cursor.close();
    }
}