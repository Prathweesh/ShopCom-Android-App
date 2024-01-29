package com.example.shopcom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {
    TextView detailDesc, detailName,detailPrice;
    ImageView detailImage;
    MyDatabaseHelper myDB;
    Button addtocart , addtowishlist;
    String img,desc,name,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        myDB = new MyDatabaseHelper(this);
        detailName = findViewById(R.id.naming);
        detailDesc = findViewById(R.id.desc);
        detailPrice = findViewById(R.id.pricing);
        detailImage  = findViewById(R.id.imagy);

        addtocart = findViewById(R.id.cart_button);
        addtowishlist = findViewById(R.id.wishlist_button);

        String username = SharedData.sharedString;


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            desc = bundle.getString("Description");
            detailDesc.setText("Description : "+desc);
            name = bundle.getString("Name");
            detailName.setText(name);
            price = bundle.getString("Price");
            detailPrice.setText("Price : ₹"+price);
            img = bundle.getString("Image");
            Picasso.get()
                    .load(img)
                    .into(detailImage);
        }
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.insertintocart(username,name,price,desc,img);
            }
        });
        addtowishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.insertintowishlist(username,name,price,desc,img);
            }
        });
    }
}
