package com.example.shopcom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView t1;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView headerTextView = headerView.findViewById(R.id.textView3);

        Intent receivedIntent = getIntent();
        String extraValue = receivedIntent.getStringExtra("Name");
        SharedData.sharedString = extraValue;

        headerTextView.setText("Welcome, "+extraValue);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int select = item.getItemId();
            if(select == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            }

            if(select == R.id.nav_profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            }

            if(select == R.id.nav_cart){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
            }

            if(select == R.id.nav_wishlist){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WishlistFragment()).commit();
            }

            if(select == R.id.nav_order){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrdersFragment()).commit();
            }

            if(select == R.id.nav_logout) {
                finishAffinity();
                Toast.makeText(this, "Successfully Logged Out!", Toast.LENGTH_SHORT).show();
            }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

}