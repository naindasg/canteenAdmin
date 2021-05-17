package com.example.canteenadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {
    Meal meal;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        //Getting data from MenuActivity
        Intent intent = getIntent();
        String mealID = intent.getStringExtra("mealID");
        String mealName = intent.getStringExtra("mealName");
        String mealDescription = intent.getStringExtra("mealDescription");
        String mealPrice = intent.getStringExtra("mealPrice");
        String imageUrl = intent.getStringExtra("mealImage");
        String mealIngredients = intent.getStringExtra("mealIngredients");

        //Getting views
        EditText nameView = (EditText) findViewById(R.id.nameu);
        EditText descriptionView = (EditText) findViewById(R.id.descriptionu);
        EditText priceView = (EditText) findViewById(R.id.priceu);
        EditText urlView = (EditText) findViewById(R.id.urlu);
        EditText idView = (EditText) findViewById(R.id.mealIDu);
        EditText ingredientsView = (EditText) findViewById(R.id.ingredientsu);

        //Adding data to the views
        nameView.setText(mealName);
        descriptionView.setText(mealDescription);
        priceView.setText(mealPrice);
        idView.setText(mealID);
        ingredientsView.setText(mealIngredients);
        urlView.setText(imageUrl);

        //Declaring "Save" button
        Button saveButton = (Button) findViewById(R.id.saveButtonu);

        ref = FirebaseDatabase.getInstance().getReference("/meals");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting views from updateActivity form
                EditText nameView = (EditText) findViewById(R.id.nameu);
                EditText descriptionView = (EditText) findViewById(R.id.descriptionu);
                EditText priceView = (EditText) findViewById(R.id.priceu);
                EditText urlView = (EditText) findViewById(R.id.urlu);
                EditText ingredientsView = (EditText) findViewById(R.id.ingredientsu);

                //Extracting data from views
                String name = nameView.getText().toString();
                String description = descriptionView.getText().toString();
                String price = priceView.getText().toString();
                String ingredients = ingredientsView.getText().toString();
                String url = urlView.getText().toString();

                //Hashmap object is used to update the meal in the database
                HashMap hashMap = new HashMap();

                if (TextUtils.isEmpty(name)) {
                    nameView.setError("Please enter a name");
                    return;
                } else {
                    hashMap.put("name", name.toLowerCase());
                }

                if (TextUtils.isEmpty(description)) {
                    descriptionView.setError("Please enter a description");
                    return;
                } else {
                    hashMap.put("description", description.toLowerCase());
                }

                if (TextUtils.isEmpty(ingredients)) {
                    ingredientsView.setError("Please enter some ingredients or type N/A");
                    return;
                } else {
                    hashMap.put("ingredients", ingredients.toLowerCase());
                }

                if (TextUtils.isEmpty(price)) {
                    priceView.setError("Please enter a price");
                    return;
                } else if (price.contains("£")) {
                    priceView.setError("Please dont enter a £ sign");
                    return;
                } else if (!validatePrice(price)) {
                    priceView.setError("Please enter a price in the format 10.00 or 10");
                    return;
                } else {
                    hashMap.put("price", price);
                }

                if (TextUtils.isEmpty(url)) {
                    urlView.setError("Please enter a url");
                    return;
                } else {
                    hashMap.put("url", url);
                }

                //if mealID < 10 is used because firebase starts from 01 (instead of 1)
                if (Integer.parseInt(mealID) < 10) {
                    ref.child("meal0" + mealID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(UpdateActivity.this, "Meal updated successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    ref.child("meal" + mealID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(UpdateActivity.this, "Meal updated successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        //Handle "delete" button
        Button deletebutton = (Button) findViewById(R.id.deleteButton);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("meal" + mealID).removeValue();
                Toast.makeText(UpdateActivity.this, "Meal deleted successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);

        MenuItem logout = menu.findItem(R.id.logoutFromMealDetail);

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

    public boolean validatePrice(String price) {
        try {
            Double.parseDouble(price);
        } catch (Exception e) {
            Log.d("TAG", "validatePrice: " + e.getMessage());
            return false;
        }
        return true;
    }
}