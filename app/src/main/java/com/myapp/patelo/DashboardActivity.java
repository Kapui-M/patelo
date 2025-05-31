package com.myapp.patelo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private ImageView profileIcon, eventsIcon, connectionsIcon, messagesIcon;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        profileIcon = findViewById(R.id.profileIcon);
        eventsIcon = findViewById(R.id.eventsIcon);
        connectionsIcon = findViewById(R.id.connectionsIcon);
        messagesIcon = findViewById(R.id.messagesIcon);
        welcomeText = findViewById(R.id.welcomeText);

        // Set onClick actions
        profileIcon.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        eventsIcon.setOnClickListener(v -> startActivity(new Intent(this, ShareEventActivity.class)));
        connectionsIcon.setOnClickListener(v -> startActivity(new Intent(this, FindConnectionsActivity.class)));
        messagesIcon.setOnClickListener(v -> startActivity(new Intent(this, MessageActivity.class)));
    }
}
