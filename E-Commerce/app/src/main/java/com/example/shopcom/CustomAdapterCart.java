package com.example.shopcom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class CustomAdapterCart extends RecyclerView.Adapter<CustomAdapterCart.MyViewHolder> {
    Context context;
    MyDatabaseHelper myDB;
    ArrayList<String> name, description, price,image;
    TextView totalPriceTextView;
    ArrayList<Integer> quantityList;
    int i;
    //ArrayList<byte[]> image;

    CustomAdapterCart(Context context, ArrayList<String> name, ArrayList<String> description, ArrayList<String> price, ArrayList<String> image, TextView totalPriceTextView) {
        this.context = context;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.totalPriceTextView = totalPriceTextView;
        quantityList = new ArrayList<>();
        for (int i = 0; i < price.size(); i++) {
            quantityList.add(1);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_rows, parent, false);
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
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("Name", name.get(holder.getAdapterPosition()));
                intent.putExtra("Description", description.get(holder.getAdapterPosition()));
                intent.putExtra("Price", price.get(holder.getAdapterPosition()));
                intent.putExtra("Image", image.get(holder.getAdapterPosition()));
                context.startActivity(intent);
                Toast.makeText(context,"Opening",Toast.LENGTH_SHORT).show();
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    myDB = new MyDatabaseHelper(context);
                    String username = SharedData.sharedString;
                    myDB.removefromcart(username, name.get(position));

                    name.remove(holder.getAdapterPosition());
                    description.remove(holder.getAdapterPosition());
                    price.remove(holder.getAdapterPosition());
                    image.remove(holder.getAdapterPosition());
                    quantityList.remove(holder.getAdapterPosition());

                    notifyItemRemoved(holder.getAdapterPosition());

                    calculateTotalPrice();
                    updateTotalPrice();

            }
        });
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = holder.getAdapterPosition();
                increaseQuantity(selectedPosition);
                holder.quantity.setText(String.valueOf(quantityList.get(position)));
            }
        });
        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = holder.getAdapterPosition();
                decreaseQuantity(selectedPosition);
                holder.quantity.setText(String.valueOf(quantityList.get(position)));
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
        TextView name_text, description_text, price_text ,quantity;
        ImageView image_view;
        CardView card;
        Button remove,increase,decrease;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_text = itemView.findViewById(R.id.product_name);
            description_text = itemView.findViewById(R.id.product_description);
            price_text = itemView.findViewById(R.id.product_price);
            image_view = itemView.findViewById(R.id.product_image);
            card= itemView.findViewById(R.id.product_card);
            remove=itemView.findViewById(R.id.remove);
            quantity=itemView.findViewById(R.id.product_quantity);
            increase = itemView.findViewById(R.id.increment);
            decrease = itemView.findViewById(R.id.decrement);
        }
    }
    private void increaseQuantity(int position) {
        int currentQuantity = quantityList.get(position);
        quantityList.set(position, currentQuantity + 1);

        updateTotalPrice();

        //notifyItemChanged(position);
    }
    private void decreaseQuantity(int position) {
        int currentQuantity = quantityList.get(position);
        if (currentQuantity > 1) {
            quantityList.set(position, currentQuantity - 1);

            updateTotalPrice();

            //notifyItemChanged(position);
        }
    }
    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        totalPriceTextView.setText("Total Price: ₹" + totalPrice);
    }


    private double calculateTotalPrice() {
        double total = 0.0;
        for (int i = 0; i < name.size(); i++) {
            double productPrice = Double.parseDouble(price.get(i));
            int productQuantity = quantityList.get(i);
            total += (productPrice * productQuantity);
        }
        return total;
    }
}

