package com.example.canteenadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFoodActivity extends AppCompatActivity {

    EditText mealIDView, nameView, descriptionView, ingredientsView, priceView, urlView;
    Button saveButton;
    DatabaseReference ref;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //Getting views
        mealIDView = (EditText) findViewById(R.id.mealID);
        nameView = (EditText) findViewById(R.id.name);
        descriptionView = (EditText) findViewById(R.id.description);
        ingredientsView = (EditText) findViewById(R.id.ingredients);
        priceView = (EditText) findViewById(R.id.price);
        urlView = (EditText) findViewById(R.id.url);

        //Getting saveButton
        saveButton = (Button) findViewById(R.id.saveButton);

        //Pre-poulating the mealID
        ref = FirebaseDatabase.getInstance().getReference("/meals");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mealID = null;
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        mealID = dataSnapshot.child("mealID").getValue().toString();
                    }
                    int mealIDInt = Integer.parseInt(mealID);
                    mealIDView.setText(String.valueOf(mealIDInt + 1));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("AddFoodActivity", "onCancelled: " + error.getMessage());
            }
        });

        checkIfMealAlreadyExists();



        //Handle the "Save" button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating a meal object
                Meal meal = new Meal();

                //Getting the data from views
                String mealID = mealIDView.getText().toString();
                String name = nameView.getText().toString();
                String description = descriptionView.getText().toString();
                String ingredients = ingredientsView.getText().toString();
                String price = priceView.getText().toString();
                String url = urlView.getText().toString();


                boolean mealExists = false;
                for (int i = 0; i < mealArray.size(); i++) {
                    if (mealArray.get(i).equals(mealID)) {
                        mealExists = true;
                    }
                }

                if (TextUtils.isEmpty(mealID)) {
                    mealIDView.setError("Please enter a meal ID");
                    return;
                } else if (!validateMealID(mealID)) {
                    mealIDView.setError("Please enter a number for a mealID");
                    return;
                } else if (mealExists) {
                    mealIDView.setError("Please enter a UNIQUE number for a mealID");
                    return;
                } else {
                    meal.setMealID(mealID);
                }

                if (TextUtils.isEmpty(name)) {
                    nameView.setError("Please enter a name");
                    return;
                } else {
                    meal.setName(name.toLowerCase());
                }

                if (TextUtils.isEmpty(description)) {
                    descriptionView.setError("Please enter a description");
                    return;
                } else {
                    meal.setDescription(description.toLowerCase());
                }

                if (TextUtils.isEmpty(ingredients)) {
                    ingredientsView.setError("Please enter some ingredients or type N/A");
                    return;
                } else {
                    meal.setIngredients(ingredients.toLowerCase());
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
                    meal.setPrice(price);
                }

                if (TextUtils.isEmpty(url)) {
                    urlView.setError("Please enter a url or type N/A");
                    return;
                } else {
                    meal.setUrl(url);
                }

                //if mealID < 10 is used because firebase starts from 01 (instead of 1)
                if (Integer.parseInt(mealID) < 10) {
                    ref.child("meal0" + mealID).setValue(meal);
                    Toast.makeText(AddFoodActivity.this, "Meal added successfully", Toast.LENGTH_LONG).show();
                } else {
                    ref.child("meal" + mealID).setValue(meal);
                    Toast.makeText(AddFoodActivity.this, "Meal added successfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Handle the go back button
        Button goBack = (Button) findViewById(R.id.goBackFromAdd);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public boolean validateMealID(String mealID) {
        try {
            Integer.parseInt(mealID);
        } catch (Exception e) {
            Log.d("TAG", "validateMealId: " + e.getMessage());
            return false;
        }
        return true;
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

    ArrayList<String> mealArray = new ArrayList<String>();

    public void checkIfMealAlreadyExists() {
        ref = FirebaseDatabase.getInstance().getReference("/meals");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mealID = null;
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        mealID = dataSnapshot.child("mealID").getValue().toString();
                        mealArray.add(mealID);
                        Log.d("TAG", "onDataChange: " + mealID);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("AddFoodActivity", "onCancelled: " + error.getMessage());
            }
        });
    }


}