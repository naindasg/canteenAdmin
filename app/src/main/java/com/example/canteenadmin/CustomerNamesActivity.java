package com.example.canteenadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerNamesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    CustomerNamesAdapter adapter; // Create Object of the Adapter class
    DatabaseReference ref; // Create object of the Firebase Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_names);

        //Creating a reference and inserting data into recyclerview
        ref = FirebaseDatabase.getInstance().getReference("/customerName");
        recyclerView = findViewById(R.id.customer_orders_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FirebaseRecyclerOptions<CustomerNames> options = new FirebaseRecyclerOptions.Builder<CustomerNames>().setQuery(ref, CustomerNames.class).build();
        adapter = new CustomerNamesAdapter(options);
        recyclerView.setAdapter(adapter);

        //Swipe the order to delete it
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
                String name = adapter.getSnapshots().getSnapshot(viewHolder.getAdapterPosition()).child("customerName").getValue().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/orders/" + name);
                ref.removeValue();
            }
        }).attachToRecyclerView(recyclerView);

        //Click on the customers name item to view their order
        adapter.setOnItemClickListener(new CustomerNamesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CustomerNames customerNames) {
                Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
                intent.putExtra("customerName", customerNames.getCustomerName());
                intent.putExtra("customerEmail", customerNames.getCustomerEmail());
                startActivity(intent);
            }
        });

        //View menu
        Button viewMenu = (Button) findViewById(R.id.viewMenu);
        viewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        //View previous orders
        Button viewOrderHistory = (Button) findViewById(R.id.viewOrderHistory);
        viewOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderHistoryNames.class);
                startActivity(intent);
            }
        });
    }

    //Stop retrieving data from database
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //Retrieve data from the database
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    //To prevent going back to the login screen
    @Override
    public void onBackPressed() {
        //Nothing happens
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
}


