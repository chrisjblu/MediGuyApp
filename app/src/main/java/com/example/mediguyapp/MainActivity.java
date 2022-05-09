package com.example.mediguyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collection;

import util.JournalApi;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    private Button createBtn;

    private AutoCompleteTextView email_addy;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener onAuthListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        firebaseAuth = FirebaseAuth.getInstance();



        loginBtn = findViewById(R.id.loginbtn);
        createBtn = findViewById(R.id.createbtn);
       email_addy = findViewById(R.id.email_et);
        password = findViewById(R.id.pass_et);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class
                ));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginEmailPasswordUser(email_addy.getText().toString().trim(),
                        password.getText().toString().trim());

            }
        });


    }

    private void loginEmailPasswordUser(String email, String pass) {

        if(!TextUtils.isEmpty(email)
        && !TextUtils.isEmpty(pass)){
firebaseAuth.signInWithEmailAndPassword(email, pass)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                assert user != null;
                String currentUserID = user.getUid();

                /// look through user ids to find current user

                collectionReference
                        .whereEqualTo("userId", currentUserID)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {

                                if(error != null){

                                }
                                // query
                                assert queryDocumentSnapshots != null;
                                if(!queryDocumentSnapshots.isEmpty()){
                                    for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                        JournalApi journalApi = JournalApi.getInstance();
                                        journalApi.setUsername(snapshot.getString("username"));
                                        journalApi.setUserId(currentUserID);

                                        ///intent to list
                                        startActivity(new Intent(MainActivity.this,
                                               DashboardActivity.class));


                                    }


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

        }else{
            Toast.makeText(MainActivity.this,
                    "Enter emaol & password", Toast.LENGTH_LONG).show();;
        }
    }
}