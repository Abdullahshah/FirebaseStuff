package com.example.abdullah.firebaseloginpractice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText mEmailField;
    private EditText mPasswordField;

    private Button signupButton;
    private Button loginButton;
    private Button logoutButton;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        firstName = (EditText) findViewById(R.id.editText3);
        mEmailField = (EditText) findViewById(R.id.editText);
        mPasswordField = (EditText) findViewById(R.id.editText2);

        signupButton = (Button) findViewById(R.id.button);
        loginButton = (Button) findViewById(R.id.button2);
        logoutButton = (Button) findViewById(R.id.button3);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.i("Info", "User Signed In: " + user.getUid());

                    //finish();
                }
                else
                    Log.i("Info", "No User Signed In");
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

    public void clickButton(View view) {
        if (view.getId() == R.id.button) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
        else if(view.getId() == R.id.button2)
        {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            startActivity(new Intent(this, ProfileActivity.class));
        }
        else if(view.getId() == R.id.button3)
        {
            mAuth.signOut();
        }
    }

    public void createAccount(String email, String password) {
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("Info", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            saveUserInformation();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i("Sign In Info", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.i("Sign In Info", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getApplicationContext(), "Failed",
                                    Toast.LENGTH_SHORT).show();
                        } else
                        {
                            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void saveUserInformation()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String name = firstName.getText().toString();
        String email = mEmailField.getText().toString();

        UserInformation userInformation = new UserInformation(name, email);
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(getApplicationContext(), "Information Saved to Database", Toast.LENGTH_SHORT).show();
        }
    }





