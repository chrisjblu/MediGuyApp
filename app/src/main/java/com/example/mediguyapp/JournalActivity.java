package com.example.mediguyapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import model.Journal;
import util.JournalApi;

public class JournalActivity extends AppCompatActivity implements View.OnClickListener {


    // declaring variables
    public static final int GALLERY_CODE = 1;
    private static final String TAG = "PostJournalAcitivty";

    private Button postBtn;
    private ProgressBar progressBar;
    private ImageView addphotobutton;
    private EditText titleEditText;
    private EditText postEntryEditText;
    private TextView currentUserTextView;
    private ImageView postImg;
    private ImageView post_imagebck;

    private String currentId;
    private String currentUserName;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private ActivityResultLauncher<Intent> activityResultLauncher;


    ///connecting to firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private CollectionReference collectionReference = db.collection("Journal");



    BottomNavigationView bottomNavigationView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.postprogressBar);
        titleEditText = findViewById(R.id.post_title_ed);
        postEntryEditText = findViewById(R.id.post_Entry_ed);
        currentUserTextView = findViewById(R.id.post_username_tv);

        post_imagebck = findViewById(R.id.imagebck);
        postBtn = findViewById(R.id.post_journal_btn);
        postBtn.setOnClickListener(this);
        postImg = findViewById(R.id.postimageButton);
        postImg.setOnClickListener(this);

        progressBar.setVisibility(View.INVISIBLE); /// Progress bar is invibile from launch

        if(JournalApi.getInstance() != null) {
            currentId = JournalApi.getInstance().getUserId();
            currentUserName = JournalApi.getInstance().getUsername();

            currentUserTextView.setText(currentUserName);

        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null){

                }else{

                }
            }
        };



//        bottomNavigationView = findViewById(R.id.bottom_navigator);
//        bottomNavigationView.setSelectedItemId(R.id.journal);
//
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch(item.getItemId())
//                {
//                    case R.id.dashboard:
//                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.journal:
//                        return true;
//
//                    case R.id.settings:
//                        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.newsapi:
//                        startActivity(new Intent(getApplicationContext(),NewsActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_journal_btn:
                //post the journal
                postJournal();
                break;
            case R.id.postimageButton:
                // opening te gallary on phone
                Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                imageIntent.setType("image/*");
                startActivityForResult(imageIntent, GALLERY_CODE);
                break;
        }


        }


        /// When the post journal button is clicked
    private void postJournal() {
        //whatever the user types into the edit text field of Title and post entry will be stored in the variables "title" & "post Entry"
        String title = titleEditText.getText().toString().trim();
        String postEntry = postEntryEditText.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE); /// the progress bar will be visible

/// if the title field and postentry field is not empty and an image has been chosen continue
        if(!TextUtils.isEmpty(title) &&
        !TextUtils.isEmpty(postEntry)
        && imageUri != null){

            final StorageReference filepath = storageReference //.. journa_images/your_image.jepg
                    .child("journal_images") // folder is created on firebase called Journal_images
                    .child("my_image_" + Timestamp.now().getSeconds()); //timestamp of image


        filepath.putFile(imageUri) // putting file into a storage reference
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String imageUrl = uri.toString();

                                //invoking journal
                                Journal journal = new Journal();
                                journal.setTitle(title);
                                journal.setJournalEntry(postEntry);
                                journal.setImageUrl(imageUrl);
                                journal.setTimedAdded(new Timestamp(new Date()));
                                journal.setUserName(currentUserName);
                                journal.setUserId(currentId);

                                ///invoking collection of references
                                collectionReference.add(journal)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                startActivity(new Intent(JournalActivity.this,JournalListActivity.class));
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: " + e.getMessage());

                                            }
                                        });

                            }
                        });



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });
        }else{
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE && resultCode == RESULT_OK){
            //if statement make sure data is not null
            if(data != null){
                imageUri = data.getData();
                post_imagebck.setImageURI(imageUri); //show image in the background
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuth != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
