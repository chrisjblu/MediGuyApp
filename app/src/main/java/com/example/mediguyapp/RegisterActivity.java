package com.example.mediguyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.JournalApi;

public class RegisterActivity extends AppCompatActivity {
    private Button loginBtn;
    private Button regBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    //connect to firestore
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    ///creating Users collection
    private CollectionReference collectionReference = db.collection("Users");


    private EditText emailEditText;
    private EditText passwordEditText;
    private Button createAccountButton;
    private EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();


        firebaseAuth = FirebaseAuth.getInstance();

        createAccountButton = findViewById(R.id.reg_btn);
        emailEditText = findViewById(R.id.email_signup);
        passwordEditText = findViewById(R.id.password_signup);
        userNameEditText = findViewById(R.id.username_signup);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                currentUser = firebaseAuth.getCurrentUser();;
                if  (currentUser != null){
                    //user logged in

                }else{
                    //user not logged in
                }
            }
        };
        /// when you press the create acccount button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// if the textfields of email password and username are not empty
                if (!TextUtils.isEmpty(emailEditText.getText().toString())
                        && !TextUtils.isEmpty(passwordEditText.getText().toString())
                        && !TextUtils.isEmpty(userNameEditText.getText().toString())) {

                    String email = emailEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();
                    String username = userNameEditText.getText().toString().trim();

                    createUserEmailAccount(email, password, username);

                }else {
                    Toast.makeText(RegisterActivity.this,
                            "Empty Fields Not Allowed",
                            Toast.LENGTH_LONG)
                            .show();
                }



            }
        });

    }

    private void createUserEmailAccount(String email, String password, final String username) {

        // if the email, username and password is not empty
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(username)) {


            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                final String currentUserId = currentUser.getUid();

                                // Creating a user map that will allow the creation of a user in the collection
                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("username", username);

                                //saving to firestore database
                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override

                                                            /// take the username from object
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                                                    String name = task.getResult()
                                                                            .getString("username");


                                                                    JournalApi journalApi = JournalApi.getInstance(); //GLobal APi
                                                                    journalApi.setUserId(currentUserId);
                                                                    journalApi.setUsername(name);


                                                                    /// take username and userid and redirect to dashboard
                                                                    Intent intent = new Intent(RegisterActivity.this,
                                                                            DashboardActivity.class);
                                                                    intent.putExtra("username", name);
                                                                    intent.putExtra("userId", currentUserId);
                                                                    startActivity(intent);


                                                                }else {

                                                                }

                                                            }
                                                        });

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });


                            }else {
                                //something went wrong
                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }else {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);


    }
}