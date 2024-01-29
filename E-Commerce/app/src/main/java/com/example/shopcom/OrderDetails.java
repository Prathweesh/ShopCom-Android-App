package com.example.shopcom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OrderDetails extends AppCompatActivity {
    TextView detailDesc, detailName,detailPrice;
    ImageView detailImage;
    MyDatabaseHelper myDB;
    String img,desc,name,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        myDB = new MyDatabaseHelper(this);
        detailName = findViewById(R.id.name);
        detailDesc = findViewById(R.id.description);
        detailPrice = findViewById(R.id.price);
        detailImage  = findViewById(R.id.image);


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
    }
}
