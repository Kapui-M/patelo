package com.myapp.patelo;

import android.os.Bundle;

//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
/*import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;*/
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {

    private TextView fullNameText, accountTypeText, dashboardStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // UI Bindings
        fullNameText = findViewById(R.id.fullNameText);
        accountTypeText = findViewById(R.id.accountTypeText);
        dashboardStatus = findViewById(R.id.dashboardStatus);
        ImageView profileImage = findViewById(R.id.profileImage);

        Button viewProfileBtn = findViewById(R.id.viewProfileBtn);
        Button shareEventBtn = findViewById(R.id.shareEventBtn);
        Button findConnectionsBtn = findViewById(R.id.findConnectionsBtn);

        // Load data
        if (mAuth.getCurrentUser() != null) {
            String uid = mAuth.getCurrentUser().getUid();

            db.collection("users").document(uid)
                    .get()
                    .addOnSuccessListener(document -> {
                        if (document.exists()) {
                            String fullName = document.getString("fullName");
                            String accountType = document.getString("accountType");

                            fullNameText.setText(fullName);
                            accountTypeText.setText(accountType != null ? accountType : "Standard");
                            dashboardStatus.setText("Welcome back!");
                        } else {
                            dashboardStatus.setText("User data not found.");
                        }
                    })
                    .addOnFailureListener(e -> dashboardStatus.setText(R.string.loading_error));
        } else {
            dashboardStatus.setText(R.string.not_logged_in);
        }

        // Add basic click behavior (we’ll implement pages later)
        viewProfileBtn.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

        shareEventBtn.setOnClickListener(v -> startActivity(new Intent(this, ShareEventActivity.class)));

        findConnectionsBtn.setOnClickListener(v -> startActivity(new Intent(this, FindConnectionsActivity.class)));
    }
}