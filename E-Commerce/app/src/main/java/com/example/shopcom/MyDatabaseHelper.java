package com.example.shopcom;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME="Shopcom.db";
    public static final int DATABASE_VERSION = 1;
    public static final String PRODUCT_TABLE = "products";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PRICE ="price";
    public static final String PRODUCT_DESCRIPTION ="description";
    public static final String PRODUCT_CATEGORY ="category";
    public static final String PRODUCT_IMAGE ="image";

    public static final String USER_TABLE = "users";
    public static final String USER_NAME = "name";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone";
    public static final String USER_ADDRESS = "address";
    public static final String CART_TABLE = "cart";
    public static final String CART_USER_NAME = "username";
    public static final String CART_NAME = "name";
    public static final String CART_PRICE ="price";
    public static final String CART_DESCRIPTION ="description";
    public static final String CART_IMAGE ="image";
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_USER_NAME = "username";
    public static final String ORDER_NAME = "name";
    public static final String ORDER_PRICE ="price";
    public static final String ORDER_DESCRIPTION ="description";
    public static final String ORDER_IMAGE ="image";
    public static final String WISHLIST_TABLE = "wishlist";
    public static final String WISHLIST_USER_NAME = "username";
    public static final String WISHLIST_NAME = "name";
    public static final String WISHLIST_PRICE ="price";
    public static final String WISHLIST_DESCRIPTION ="description";
    public static final String WISHLIST_IMAGE ="image";
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+PRODUCT_TABLE+
                " ("+PRODUCT_NAME+" TEXT PRIMARY KEY, "
                +PRODUCT_PRICE+" INTEGER, "
                +PRODUCT_DESCRIPTION+ " TEXT, "
                +PRODUCT_CATEGORY+" TEXT, "
                +PRODUCT_IMAGE+" TEXT);";
        db.execSQL(query);
       query = "CREATE TABLE "+USER_TABLE+
                " ("+USER_NAME+" TEXT PRIMARY KEY, "
               +USER_EMAIL+" TEXT, "
               +USER_PHONE+" TEXT, "
               +USER_ADDRESS+ " TEXT, "
                +USER_PASSWORD+" TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE "+CART_TABLE+
                " ("+CART_NAME+" TEXT, "
                +CART_USER_NAME+" INTEGER, "
                +CART_PRICE+" INTEGER, "
                +CART_DESCRIPTION+ " TEXT, "
                +CART_IMAGE+" TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE "+ORDER_TABLE+
                " ("+ORDER_NAME+" TEXT, "
                +ORDER_USER_NAME+" INTEGER, "
                +ORDER_PRICE+" INTEGER, "
                +ORDER_DESCRIPTION+ " TEXT, "
                +ORDER_IMAGE+" TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE "+WISHLIST_TABLE+
                " ("+WISHLIST_NAME+" TEXT, "
                +WISHLIST_USER_NAME+" INTEGER, "
                +WISHLIST_PRICE+" INTEGER, "
                +WISHLIST_DESCRIPTION+ " TEXT, "
                +WISHLIST_IMAGE+" TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE + ", " + USER_TABLE + ", " + CART_TABLE + ", " + WISHLIST_TABLE + ", " + ORDER_TABLE);
        onCreate(db);
    }
    Cursor readAllData(String cat){
        if(cat.equals("all products")) {
            String query = "SELECT * FROM " + PRODUCT_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            if (db != null)
                cursor = db.rawQuery(query, null);
            return cursor;
        }
        else {
            String query = "SELECT * FROM " + PRODUCT_TABLE + " WHERE category = '" + cat + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            if (db != null)
                cursor = db.rawQuery(query, null);
            return cursor;
        }
    }

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + PRODUCT_TABLE + " WHERE name = 'Crocs';";
        db.execSQL(query);
    }
    public Boolean insertData(String name, String password ,String email , String phone , String address){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, name);
        contentValues.put(USER_EMAIL, email);
        contentValues.put(USER_PHONE, phone);
        contentValues.put(USER_ADDRESS, address);
        contentValues.put(USER_PASSWORD, password);
        long result = MyDatabase.insert(USER_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkName(String name){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where name = ?", new String[]{name});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkNamePassword(String name, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where name = ? and password = ?", new String[]{name, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public void insertintocart(String user_name,String item_Name,String item_Price,String item_Desc,String item_Image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CART_NAME, item_Name);
        values.put(CART_USER_NAME,user_name);
        values.put(CART_PRICE, item_Price);
        values.put(CART_DESCRIPTION,item_Desc);
        values.put(CART_IMAGE,item_Image);
        db.insert(CART_TABLE, null, values);

        Toast.makeText(context,"Added to Cart",Toast.LENGTH_SHORT).show();
    }
    public void insertintowishlist(String user_name,String item_Name,String item_Price,String item_Desc,String item_Image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WISHLIST_NAME, item_Name);
        values.put(WISHLIST_USER_NAME,user_name);
        values.put(WISHLIST_PRICE, item_Price);
        values.put(WISHLIST_DESCRIPTION,item_Desc);
        values.put(WISHLIST_IMAGE,item_Image);
        db.insert(WISHLIST_TABLE, null, values);

        Toast.makeText(context,"Added to Wishlist",Toast.LENGTH_SHORT).show();
    }
    Cursor readCartData(String name){
            String query = "SELECT * FROM " + CART_TABLE + " WHERE username = '" + name + "'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            if (db != null)
                cursor = db.rawQuery(query, null);
            return cursor;
    }
    Cursor readWishlistData(String name){
        String query = "SELECT * FROM " + WISHLIST_TABLE + " WHERE username = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null)
            cursor = db.rawQuery(query, null);
        return cursor;
    }
    Cursor readOrderData(String name){
        String query = "SELECT * FROM " + ORDER_TABLE + " WHERE username = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null)
            cursor = db.rawQuery(query, null);
        return cursor;
    }
    public void removefromwishlist(String personusername, String productname) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + WISHLIST_TABLE + " WHERE username = ? AND name = ?";
        db.execSQL(query, new String[]{personusername, productname});
        db.close();
    }
    public void removefromcart(String personusername, String productname) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + CART_TABLE + " WHERE username = ? AND name = ?";
        db.execSQL(query, new String[]{personusername, productname});
        db.close();
    }
    public void clearCart(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = "username = ?";
        String[] whereArgs = {username};

        db.delete(CART_TABLE, whereClause, whereArgs);

        db.close();
    }

    Cursor readUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE + " WHERE name = ?";
        String[] selectionArgs = { username };
        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, selectionArgs);
        }

        return cursor;
    }
    public void moveItemsFromCartToOrders(String name) {
        String query = "SELECT * FROM " + CART_TABLE + " WHERE username = '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null)
            cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {

            String productName = cursor.getString(0);
            String productPrice = cursor.getString(2);
            String productDescription = cursor.getString(3);
            String productImage = cursor.getString(4);

            ContentValues values = new ContentValues();
            values.put(ORDER_NAME, productName);
            values.put(ORDER_USER_NAME, name);
            values.put(ORDER_PRICE, productPrice);
            values.put(ORDER_DESCRIPTION, productDescription);
            values.put(ORDER_IMAGE, productImage);

            db.insert(ORDER_TABLE, null, values);
        }
    }
    public void insertData() {
        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap imageBitmap;
        byte[] imageData;
        ByteArrayOutputStream stream;
        ContentValues values = new ContentValues();


        values.put(PRODUCT_NAME, "TV");
        values.put(PRODUCT_PRICE, 63299);
        values.put(PRODUCT_DESCRIPTION, "Enjoy immersive visuals with equally exciting audio on the OnePlus Y Series 100 cm (40) Full HD LED Smart Android TV that comes with a number of innovative features. This home entertainment appliance features 93% Colour Gamut for lifelike vibrant visuals, a Gamma Engine for high-quality viewing, and Dolby Audio for an enhanced sound experience.");
        values.put(PRODUCT_CATEGORY, "electronics");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/landscape-nature-scene-tv-appliance-generative-ai_188544-12122.jpg?w=1060&t=st=1698015879~exp=1698016479~hmac=0199cbc4ffd171cb171a09528e8df6cc336a0a40b37248c48cf7a3117cca3ae8");
        db.insert(PRODUCT_TABLE,null,values);

        values.put(PRODUCT_NAME, "Laptop");
        values.put(PRODUCT_PRICE, 54230);
        values.put(PRODUCT_DESCRIPTION, "HP Victus Gaming Laptop, AMD Ryzen 5 5600H, AMD Radeon RX 6500M Graphics, 15.6-inch (39.6 cm), FHD, IPS, 144Hz, 9 ms Response time, 16GB DDR4, 512GB SSD, Backlit KB (Win 11, Blue, 2.29 kg), fb0134AX");
        values.put(PRODUCT_CATEGORY, "electronics");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/view-3d-laptop-device-with-screen-keyboard_23-2150714005.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE,null,values);


        values.put(PRODUCT_NAME, "Phone");
        values.put(PRODUCT_PRICE, 70000);
        values.put(PRODUCT_DESCRIPTION, "Apple iphoneX 64GB,NEXT-GENERATION PORTRAITS — Capture portraits with dramatically more detail and color. Just tap to shift the focus between subjects — even after you take the shot.");
        values.put(PRODUCT_CATEGORY, "electronics");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-vector/display-template-with-cellphones_79603-1243.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE,null,values);

        values.put(PRODUCT_NAME, "Washing machine");
        values.put(PRODUCT_PRICE, 32009);
        values.put(PRODUCT_DESCRIPTION, "Whirlpool 7 Kg 5 Star Royal Fully-Automatic Top Loading Washing Machine (WHITEMAGIC ROYAL 7.0 GENX, Grey, Hard Water Wash, ZPF Technology)");
        values.put(PRODUCT_CATEGORY, "electronics");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-vector/washing-machine-realistic-poster-with-quiet-quick-wash-symbols-illustration_1284-29131.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE,null,values);


        values.put(PRODUCT_NAME, "Refrigerator");
        values.put(PRODUCT_PRICE, 163299);
        values.put(PRODUCT_DESCRIPTION, "Whirlpool 240 L Frost Free Triple-Door Refrigerator (FP 263D PROTTON ROY ARCTIC STEEL(N)");
        values.put(PRODUCT_CATEGORY, "electronics");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-vector/refrigerator-organization-set_1284-23312.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE,null,values);

        values.put(PRODUCT_NAME, "Sofa");
        values.put(PRODUCT_PRICE, 5000);
        values.put(PRODUCT_DESCRIPTION, "AllModern  Home Furniture Sheesham Wood 2 Seater Sofa for Living Room Bedroom Hall Home office(Natural Finish)");
        values.put(PRODUCT_CATEGORY, "home");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-vector/interior-mockup-realistic-element-sofa-poster_1284-11224.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE, null, values);


        values.put(PRODUCT_NAME, "Wardrobe");
        values.put(PRODUCT_PRICE, 7000);
        values.put(PRODUCT_DESCRIPTION, "Solimo Alpha Door Engineered Wood Wardrobe With Drawer(Imperial Teak Finish)");
        values.put(PRODUCT_CATEGORY, "home");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-vector/realistic-wardrobe-with-clothes_52683-72860.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE, null, values);

        values.put(PRODUCT_NAME, "Chair");
        values.put(PRODUCT_PRICE, 1500);
        values.put(PRODUCT_DESCRIPTION, "Ikea ADDE Chair Indoor/Outdoor Back Rest(Steel & Polypropylene Plastic, Pack of 3)");
        values.put(PRODUCT_CATEGORY, "home");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-psd/comfortable-modern-chair_176382-906.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE, null, values);

        values.put(PRODUCT_NAME, "Mat");
        values.put(PRODUCT_PRICE, 1000);
        values.put(PRODUCT_DESCRIPTION, "Saral Home Easy Living Microfiber Anti-Skid Bath Mat Pack of 2(35X50 CM, Rectangular)");
        values.put(PRODUCT_CATEGORY, "home");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/white-slippers-doormat_53876-128134.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE, null, values);

        values.put(PRODUCT_NAME, "Mirror");
        values.put(PRODUCT_PRICE, 1200);
        values.put(PRODUCT_DESCRIPTION, "ORIGIN MIRRORS Decorative Led Light Wall Mirror For Bathroom, Washbasin Wall Mounted(Size 18 X 24)");
        values.put(PRODUCT_CATEGORY, "home");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/view-modern-entryway-with-interior-designed-furniture_23-2150791080.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE, null, values);

        values.put(PRODUCT_NAME, "Crocs");
        values.put(PRODUCT_PRICE, 2000);
        values.put(PRODUCT_DESCRIPTION, "Unisex-Adult Bayaband ClogClogs,best all rounder sleeper");
        values.put(PRODUCT_CATEGORY, "footwear");
        values.put(PRODUCT_IMAGE,"https://media.istockphoto.com/id/1031539174/photo/a-pair-of-brown-crocs-sandals-with-motif-design-isolated-on-black-background.jpg?s=612x612&w=0&k=20&c=xRNV5KfNxsnHpKgG0jhZN-NXvakPqWddvMKyNPHtVqM=");
        db.insert(PRODUCT_TABLE, null, values);


        values.put(PRODUCT_NAME, "Sneakers");
        values.put(PRODUCT_PRICE, 2500);
        values.put(PRODUCT_DESCRIPTION, "Red Tape Casual Sneaker Shoes for Men Upgraded Comfort with Cushioned Insole and Slip-Resistant Sole");
        values.put(PRODUCT_CATEGORY, "footwear");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/shoes_1203-8153.jpg?w=996&t=st=1698070955~exp=1698071555~hmac=0bd92c5b6ade06808de8cbf4d075290e37d31353f5adde6d08a8effafef2c8ab");
        db.insert(PRODUCT_TABLE, null, values);

        values.put(PRODUCT_NAME, "Boots");
        values.put(PRODUCT_PRICE, 1500);
        values.put(PRODUCT_DESCRIPTION, "Woodland Original 7-Eye Moto Inspired Mild water proof ankle boots for Men");
        values.put(PRODUCT_CATEGORY, "footwear");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/leather-boots_1203-8146.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE, null, values);

        values.put(PRODUCT_NAME, "Trekking Shoes");
        values.put(PRODUCT_PRICE, 1200);
        values.put(PRODUCT_DESCRIPTION, "Bacca Bucci Men's HUNTER 6 inches Hiking shoes for mrn for outdoor Trekking- non slip,Water Proof,Comfortable & Light weight");
        values.put(PRODUCT_CATEGORY, "footwear");
        values.put(PRODUCT_IMAGE,"https://lh3.googleusercontent.com/-s53m4MSCKRw/YJ0hgbfqKQI/AAAAAAAA-sA/9xzoyiLjxIQAl_4VFmedMKEjViLjysbNQCLcBGAsYHQ/s1600/1620910461169512-0.png");
        db.insert(PRODUCT_TABLE, null, values);

        values.put(PRODUCT_NAME, "Flipflops");
        values.put(PRODUCT_PRICE, 1200);
        values.put(PRODUCT_DESCRIPTION, "Airson Al-401 Men Stylish Lightweight Slippers Casual & Comfortable, Anti-Skid, Daily_Wear Slippers for Indoor & Outdoor");
        values.put(PRODUCT_CATEGORY, "footwear");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/blue-flip-flops-with-indigo-monstera_53876-98187.jpg?size=626&ext=jpg&uid=R47087730&ga=GA1.1.1372196961.1667314596&semt=ais");
        db.insert(PRODUCT_TABLE, null, values);

        values.put(PRODUCT_NAME, "Formal shirt");
        values.put(PRODUCT_PRICE, 3299);
        values.put(PRODUCT_DESCRIPTION, "Slim-fit shirt in an airy, pima cotton weave with a turn-down collar");
        values.put(PRODUCT_CATEGORY, "clothing");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/men-shirt-clothing_1203-8356.jpg?w=1060&t=st=1698072632~exp=1698073232~hmac=c4a568ae25ab51bd6953b0d4b777f1905660d465d9e65b4e80fb759d2715af9a");
        db.insert(PRODUCT_TABLE,null,values);




        values.put(PRODUCT_NAME, "Baseball Cap");
        values.put(PRODUCT_PRICE, 999);
        values.put(PRODUCT_DESCRIPTION, " Caps designed for  Incredibly stretchy and very breathable, It’s the perfect cricket cap, ideal for all your practice sessions, outdoor walks, hiking and traveling adventures. ");
        values.put(PRODUCT_CATEGORY, "clothing");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/empty-purple-baseball-green-white_1203-5374.jpg?w=900&t=st=1698072692~exp=1698073292~hmac=1a2dfff641c9a7b41a4a17a33a8145bf177b9ce0e5c047c8991d02087aa069c9");
        db.insert(PRODUCT_TABLE,null,values);



        values.put(PRODUCT_NAME, "Jeans");
        values.put(PRODUCT_PRICE, 4599);
        values.put(PRODUCT_DESCRIPTION, "This Solid Straight Fit Jeans, crafted with 100% cotton denim for ultimate comfort and durability.");
        values.put(PRODUCT_CATEGORY, "clothing");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/jeans_1203-8094.jpg?w=740&t=st=1698072724~exp=1698073324~hmac=61727249bc59445ec4d73d6fa41347b099701e8fdb3106b37618ee006d9b38f1");
        db.insert(PRODUCT_TABLE,null,values);



        values.put(PRODUCT_NAME, "Casual Shirt");
        values.put(PRODUCT_PRICE, 799);
        values.put(PRODUCT_DESCRIPTION, " Crafted from 100% cotton, this shirt for men looks denim yet offers the comfort of cotton ");
        values.put(PRODUCT_CATEGORY, "clothing");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/blue-dress-sleeve-boy-clothing_1203-6478.jpg?w=900&t=st=1698072750~exp=1698073350~hmac=6a8bda1efb39f4766ed65edb9acc92ed53671d4f5fbd8eacabe62755e2fbabd8");
        db.insert(PRODUCT_TABLE,null,values);



        values.put(PRODUCT_NAME, "Western Dress");
        values.put(PRODUCT_PRICE, 1499);
        values.put(PRODUCT_DESCRIPTION, "Fabric: Georgette (100% Polyester) Packet contain: 1 Readymade Dress");
        values.put(PRODUCT_CATEGORY, "clothing");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/fashion-woman-with-clothes_1203-8302.jpg?w=360&t=st=1698072778~exp=1698073378~hmac=a50749d88bc973f90d596f2791b39e07e46ab02ba181744262d0a173dd4d4891");
        db.insert(PRODUCT_TABLE,null,values);

        values.put(PRODUCT_NAME, "Dumbbell Set");
        values.put(PRODUCT_PRICE, 3299);
        values.put(PRODUCT_DESCRIPTION, "20kg Dumbbell Set for starters and gym goers");
        values.put(PRODUCT_CATEGORY, "fitness");
        values.put(PRODUCT_IMAGE,"https://media.istockphoto.com/id/182713174/photo/dumbells.jpg?s=612x612&w=0&k=20&c=6qyjNG6bez3I0wn6YfkQb9D4JK6nIcSw_l5G91tRf0g=");
        db.insert(PRODUCT_TABLE,null,values);




        values.put(PRODUCT_NAME, " Gear Cycle");
        values.put(PRODUCT_PRICE, 9899);
        values.put(PRODUCT_DESCRIPTION, "This Shimano 21 speed geared cycle comes with Disc Brakes and suspension for effortless Braking system built for your ease of use and maintenance, and excellent braking power.");
        values.put(PRODUCT_CATEGORY, "fitness");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/vintage-metal-white-bicycle-toy-wooden-table_155003-6411.jpg?w=900&t=st=1698073543~exp=1698074143~hmac=ec926940605386140c0f48961bdf0691f1463952e32f4b8c6642764dae8d445d");
        db.insert(PRODUCT_TABLE,null,values);



        values.put(PRODUCT_NAME, "Yoga Mat");
        values.put(PRODUCT_PRICE, 1599);
        values.put(PRODUCT_DESCRIPTION, "Ace those asanas with this signature Teal Yoga Mat.");
        values.put(PRODUCT_CATEGORY, "fitness");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/modern-sport-composition-with-gym-mat_23-2148000459.jpg?w=900&t=st=1698073567~exp=1698074167~hmac=e46b45386ba4950f8cf049cc11d6617f65ef32a38e308fc7f4bd6a3be344e353");
        db.insert(PRODUCT_TABLE,null,values);



        values.put(PRODUCT_NAME, "Bumper Plate");
        values.put(PRODUCT_PRICE, 16499);
        values.put(PRODUCT_DESCRIPTION, "Made with great precision and quality that is built to last.");
        values.put(PRODUCT_CATEGORY, "fitness");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-photo/big-dumbbells-white_144627-24207.jpg?w=900&t=st=1698073590~exp=1698074190~hmac=3de64728c095d4526790a9afbd73f91842f6ce45cfdeb423c88215e0231064fc");
        db.insert(PRODUCT_TABLE,null,values);



        values.put(PRODUCT_NAME, "Protein Powder");
        values.put(PRODUCT_PRICE, 2049);
        values.put(PRODUCT_DESCRIPTION, "OPTIMUM NUTRITION Performance Whey Protein Powder Blend with Isolate, 24g Protein, 5g BCAA, Chocolate, 1 kg");
        values.put(PRODUCT_CATEGORY, "fitness");
        values.put(PRODUCT_IMAGE,"https://img.freepik.com/free-vector/realistic-set-two-barbells-sport-supplement-glassy-surface-white-background-vector-illustration_1284-19886.jpg?w=900&t=st=1698073618~exp=1698074218~hmac=7e2d4b62649f7489224ac35d1730f958dcec99348a248b0c08f0b5e2dd44da1d");
        db.insert(PRODUCT_TABLE,null,values);

        values.put(PRODUCT_NAME, "Paracetamol");
        values.put(PRODUCT_PRICE, 100);
        values.put(PRODUCT_DESCRIPTION, "Paracetamol 500- Strip of 10 tablets Paracetamol is a non-opioid analgesic and antipyretic agent used to treat fever and mild to moderate pain. It is a widely used over the counter medication and common brand names include Tylenol and Panadol.");
        values.put(PRODUCT_CATEGORY, "medicine");
        values.put(PRODUCT_IMAGE,"https://media.istockphoto.com/id/1022216070/photo/packet-of-generic-paracetamol-tablets.jpg?s=612x612&w=0&k=20&c=h1RhnmC6DyHs6C3cFX_9dLHHzX-QKb8jhm_oc1Fcsug=");
        db.insert(PRODUCT_TABLE, null, values);


        values.put(PRODUCT_NAME, "Metformin");
        values.put(PRODUCT_PRICE, 250);
        values.put(PRODUCT_DESCRIPTION, "Metaformin 500- Strip of 10 tablets Metformin, sold under the brand name Glucophage, among others, is the main first-line medication for the treatment of type 2 diabetes, particularly in people who are overweight. It is also used in the treatment of polycystic ovary syndrome");
        values.put(PRODUCT_CATEGORY, "medicine");
        values.put(PRODUCT_IMAGE,"https://media.istockphoto.com/id/1369015427/photo/pharmaceuticals-surface-medicine-for-health-care.webp?s=1024x1024&w=is&k=20&c=F6ccvPH01HCmHL2RzIYPVunQhnVxNtjb_RA8QYPF6Nc=");
        db.insert(PRODUCT_TABLE, null, values);


        values.put(PRODUCT_NAME, "Exceptus-D");
        values.put(PRODUCT_PRICE, 150);
        values.put(PRODUCT_DESCRIPTION, "Exceptus-D Bottle of 100 ml Syrup   EXPECTUS D COUGH SYRUP is a combination of Chlorpheniramine and Dextromethorphan which belongs to the group of medicines called Antihistamines and Antitussives respectively. It is used to relieve cough due to throat or bronchial irritation, sneezing, and running nose caused by cold, dry cough, allergic cough, post-operative cough, smokers cough and night-time cough. ");
        values.put(PRODUCT_CATEGORY, "medicine");
        values.put(PRODUCT_IMAGE,"https://www.netmeds.com/images/product-v1/600x600/304604/expectus_d_cough_syrup_100ml_487114_0_1.jpg");
        db.insert(PRODUCT_TABLE, null, values);


        values.put(PRODUCT_NAME, "Vitamin Syrup");
        values.put(PRODUCT_PRICE, 300);
        values.put(PRODUCT_DESCRIPTION, "Multivitamin Syrup | Boosts Immunity | Powerhouse of Vitamins & Minerals | 200 ml (Pack of 2)");
        values.put(PRODUCT_CATEGORY, "medicine");
        values.put(PRODUCT_IMAGE,"https://5.imimg.com/data5/SELLER/Default/2023/4/300364522/ZD/TQ/ZT/147741354/20230412-191711-1000x1000.jpg");
        db.insert(PRODUCT_TABLE, null, values);


        values.put(PRODUCT_NAME, "Atorvast 10mg");
        values.put(PRODUCT_PRICE, 300);
        values.put(PRODUCT_DESCRIPTION, "ATORVAST 10MG TAB 15S Atorvast 10 Tablet belongs to a group of medicines called statins. It is used to lower cholesterol and to reduce the risk of heart diseases. ");
        values.put(PRODUCT_CATEGORY, "medicine");
        values.put(PRODUCT_IMAGE,"https://www.scottmorrison.in/wp-content/uploads/2022/07/LOWVAS-10.jpg");
        db.insert(PRODUCT_TABLE, null, values);






        db.close();
    }


}
