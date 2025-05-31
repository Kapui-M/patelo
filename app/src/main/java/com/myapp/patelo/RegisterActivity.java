package com.myapp.patelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;


import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    // UI Elements
    private EditText emailField, passwordField;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Connects to the XML layout

        // Get Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        EditText fullNameField = findViewById(R.id.fullNameField);
        EditText accountTypeField = findViewById(R.id.accountTypeField);

// Firestore init



        // Link UI components to code
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        Button registerButton = findViewById(R.id.registerButton);

        // Set button click behavior
        registerButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            String fullName = fullNameField.getText().toString();
            String accountType = accountTypeField.getText().toString();

            registerUser(email, password, fullName, accountType); // Call method to register
        });

        TextView goToLoginText = findViewById(R.id.goToLoginText);

        goToLoginText.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Optional: close Register screen so back button doesn't return here
        });

    }

    private void registerUser(String email, String password, String fullName, String accountType) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        // Save additional user info to Firestore
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("fullName", fullName);
                        userMap.put("email", email);
                        userMap.put("accountType", accountType);

                        db.collection("users").document(userId).set(userMap)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Registered & Saved Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, MainActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(this, "Firestore Error: " + e.getMessage(), Toast.LENGTH_LONG).show());

                    } else {
                        Toast.makeText(this, "Registration Failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}
