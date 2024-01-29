package com.example.shopcom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterOrder extends RecyclerView.Adapter<CustomAdapterOrder.MyViewHolder> {
    Context context;
    ArrayList<String> name, description, price,image;
    //ArrayList<byte[]> image;

    CustomAdapterOrder(Context context, ArrayList<String> name, ArrayList<String> description, ArrayList<String> price, ArrayList<String> image) {
        this.context = context;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_rows, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name_text.setText(name.get(position));
        holder.description_text.setText(description.get(position));
        holder.price_text.setText("₹" + price.get(position));

        Picasso.get()
                .load(image.get(position))
                .into(holder.image_view);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetails.class);
                intent.putExtra("Name", name.get(holder.getAdapterPosition()));
                intent.putExtra("Description", description.get(holder.getAdapterPosition()));
                intent.putExtra("Price", price.get(holder.getAdapterPosition()));
                intent.putExtra("Image", image.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        //Picasso.get().load(new File(image.get(position))).into(holder.image_view);
        //Picasso.get().setLoggingEnabled(true);
        /*byte[] imageBytes = image.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.image_view.setImageBitmap(bitmap);*/
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_text, description_text, price_text;
        ImageView image_view;
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_text = itemView.findViewById(R.id.product_name);
            description_text = itemView.findViewById(R.id.product_description);
            price_text = itemView.findViewById(R.id.product_price);
            image_view = itemView.findViewById(R.id.product_image);
            card= itemView.findViewById(R.id.product_card);
        }
    }
}

