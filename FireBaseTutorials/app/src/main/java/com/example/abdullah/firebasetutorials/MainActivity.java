package com.example.abdullah.firebasetutorials;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    //private Button fbButton;
    //private EditText name;
    //private EditText email;
    //private TextView nameField;
    //private TextView ageField;

    //private ListView listView;
    //private ArrayList<String> mUsername = new ArrayList<>();
    //private ArrayList<String> mKeys = new ArrayList<>();

    private EditText EmailField;
    private EditText PasswordField;
    private Button button;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        EmailField = (EditText) findViewById(R.id.email_field);
        PasswordField = (EditText) findViewById(R.id.password_field);
        button = (Button) findViewById(R.id.register_button);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) // User is logged in
                {
                    // Intent User Account
                }

            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
        /*
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsername);
        listView.setAdapter(arrayAdapter);

       mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue(String.class);
                mUsername.add(value);

                String key = dataSnapshot.getKey();
                mKeys.add(key);

                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();

                int index = mKeys.indexOf(key);

                mUsername.set(index, value);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // To receive data from firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue().toString();
                nameField.setText("Name: " + str);
            }

            @Override //Method if error occurs
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        fbButton = (Button) findViewById(R.id.firebaseButton);

         mDatabase = FirebaseDatabase.getInstance().getReference().child("User_01"); // Connects to database
            // add .child("User_01") to access the child branch specifically
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameString = name.getText().toString();
                final String emailString = email.getText().toString();

                // 1) Create a child in root object
                // 2) Assign value to child object

                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("Name", nameString);
                dataMap.put("Email", emailString);

                //.addOnCompleteListener to check if completed
                mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Stored", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
*/

