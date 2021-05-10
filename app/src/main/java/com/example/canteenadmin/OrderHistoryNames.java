package com.example.canteenadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderHistoryNames extends AppCompatActivity {
    private RecyclerView recyclerView;
    CustomerNamesAdapter adapter; // Create Object of the Adapter class
    DatabaseReference ref; // Create object of the Firebase Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_names);

        //Create a reference to the database and insert data into recylerView
        ref = FirebaseDatabase.getInstance().getReference("/orderHistoryName");
        recyclerView = findViewById(R.id.order_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FirebaseRecyclerOptions<CustomerNames> options = new FirebaseRecyclerOptions.Builder<CustomerNames>().setQuery(ref, CustomerNames.class).build();
        adapter = new CustomerNamesAdapter(options);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CustomerNamesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CustomerNames customerNames) {
                Intent intent = new Intent(getApplicationContext(), OrderHistoryDetailActivity.class);
                intent.putExtra("customerName", customerNames.getCustomerName());
                intent.putExtra("customerEmail", customerNames.getCustomerEmail());
                startActivity(intent);
            }
        });

    }

    //Stop retrieving data from the database
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
