package com.example.canteenadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    DatabaseReference mbase;
    MealAdapter mealAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Create a reference and insert data into the recyclerview
        mbase = FirebaseDatabase.getInstance().getReference("/meals");
        recyclerView = findViewById(R.id.menu_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Meal> options = new FirebaseRecyclerOptions.Builder<Meal>().setQuery(mbase, Meal.class).build();
        mealAdapter = new MealAdapter(options);
        recyclerView.setAdapter(mealAdapter);

        //Passing data to UpdateActivity
        mealAdapter.setOnItemClickListener(new MealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Meal meal) {
                Intent intent = new Intent(MenuActivity.this, UpdateActivity.class);
                intent.putExtra("mealID", meal.getMealID());
                intent.putExtra("mealName", meal.getName());
                intent.putExtra("mealDescription", meal.getDescription());
                intent.putExtra("mealIngredients", meal.getIngredients());
                intent.putExtra("mealPrice", meal.getPrice());
                intent.putExtra("mealImage", meal.getUrl());
                startActivity(intent);
            }
        });

        //Handles "Add food to the menu" button
        Button addFood = (Button) findViewById(R.id.addFood);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFoodActivity.class);
                startActivity(intent);
            }
        });

        //Handles "Go back to view orders" button
        Button goBack = (Button) findViewById(R.id.goBackToOrders);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomerNamesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Nothing happens
    }

    //Stop retrieving data from the database
    @Override
    protected void onStop() {
        super.onStop();
        mealAdapter.stopListening();
    }

    //Retrieve data from the database
    @Override
    protected void onStart() {
        super.onStart();
        mealAdapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);

        MenuItem logout = menu.findItem(R.id.logoutFromActivity);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                System.exit(0);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}





















