package com.myapp.patelo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ShareEventActivity extends AppCompatActivity {

    private EditText titleField, locationField, dateField, descriptionField;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_event);

        db = FirebaseFirestore.getInstance();

        titleField = findViewById(R.id.eventTitle);
        locationField = findViewById(R.id.eventLocation);
        dateField = findViewById(R.id.eventDate);
        descriptionField = findViewById(R.id.eventDescription);
        Button shareEventBtn = findViewById(R.id.btnShareEvent);

        shareEventBtn.setOnClickListener(v -> {
            String title = titleField.getText().toString().trim();
            String location = locationField.getText().toString().trim();
            String date = dateField.getText().toString().trim();
            String description = descriptionField.getText().toString().trim();

            if (title.isEmpty() || location.isEmpty() || date.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> event = new HashMap<>();
            event.put("title", title);
            event.put("location", location);
            event.put("date", date);
            event.put("description", description);
            event.put("timestamp", System.currentTimeMillis());

            db.collection("community_events")
                    .add(event)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Event shared successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // close the activity
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error sharing event: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        });
    }
}

