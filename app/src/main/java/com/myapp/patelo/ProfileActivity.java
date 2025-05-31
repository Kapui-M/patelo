package com.myapp.patelo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView fullNameText, emailText, accountTypeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Connect XML Views
        fullNameText = findViewById(R.id.fullNameText);
        emailText = findViewById(R.id.emailText);
        accountTypeText = findViewById(R.id.accountTypeText);

        // Load user profile
        loadUserProfile();
    }

    @SuppressLint("SetTextI18n")
    private void loadUserProfile() {
        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String fullName = documentSnapshot.getString("fullName");
                        String email = documentSnapshot.getString("email");
                        String accountType = documentSnapshot.getString("accountType");

                        fullNameText.setText("Name: " + fullName);
                        emailText.setText("Email: " + email);
                        accountTypeText.setText("Account Type: " + accountType);
                    }
                })
                .addOnFailureListener(e -> fullNameText.setText("Failed to load profile."));
    }
}
