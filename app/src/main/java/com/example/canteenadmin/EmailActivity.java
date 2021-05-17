package com.example.canteenadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

/*
    This class is based on the following link:
    https://codinginflow.com/tutorials/android/email-intent
 */
public class EmailActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        Intent intent = getIntent();
        String email = intent.getStringExtra("customerEmail");

        Log.d("TAG", "onCreate: " + email);

        //Finding the views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextMessage);
        sendMessage = findViewById(R.id.sendMessage);

        //Prepopulating email address
        editTextEmail.setText(email);

        //Handle the "Send message" button
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String[] recipients = email.split(",");
                String subject = editTextSubject.getText().toString();
                String message = editTextMessage.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND); //Deliver some data to someone else
                intent.putExtra(Intent.EXTRA_EMAIL, recipients); //EXTRA_EMAIL: A string[] holding email address that should be delivered
                intent.putExtra(Intent.EXTRA_SUBJECT, subject); //EXTRA_SUBJECT: A constant string holding the desired subject line of a message
                intent.putExtra(Intent.EXTRA_TEXT, message); //EXTRA_TEXT: Is used to supply the data

                intent.setType("message/rfc822"); //An electronic message comprised of a recipient, subject and message
                startActivity(Intent.createChooser(intent, "Choose your email")); //Allows the user to choose their email client
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

}