package com.myapp.patelo;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import com.myapp.patelo.models.ChatMessage;
import com.myapp.patelo.adapters.MessageAdapter;


import java.util.*;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recyclerMessages;
    private EditText editMessage;

    private FirebaseFirestore db;

    private String senderId, receiverId;
    private List<ChatMessage> messageList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerMessages = findViewById(R.id.recyclerMessages);
        editMessage = findViewById(R.id.editMessage);
        ImageButton buttonSend = findViewById(R.id.buttonSend);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        senderId = auth.getUid();
        receiverId = getIntent().getStringExtra("receiverId"); // passed from previous screen

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList, senderId);

        recyclerMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerMessages.setAdapter(messageAdapter);

        listenForMessages();

        buttonSend.setOnClickListener(v -> {
            String msg = editMessage.getText().toString().trim();
            if (!msg.isEmpty()) {
                sendMessage(msg);
                editMessage.setText("");
            }
        });
    }

    private void sendMessage(String text) {
        Map<String, Object> message = new HashMap<>();
        message.put("senderId", senderId);
        message.put("receiverId", receiverId);
        message.put("message", text);
        message.put("timestamp", new Date());

        db.collection("messages").add(message)
                .addOnFailureListener(e -> Toast.makeText(this, "Send failed", Toast.LENGTH_SHORT).show());
    }

    private void listenForMessages() {
        db.collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    messageList.clear();
                    for (DocumentSnapshot doc : Objects.requireNonNull(value).getDocuments()) {
                        String sender = doc.getString("senderId");
                        String receiver = doc.getString("receiverId");

                        // Show only if user is sender or receiver
                        if ((Objects.requireNonNull(sender).equals(senderId) && Objects.requireNonNull(receiver).equals(receiverId)) ||
                                (sender.equals(receiverId) && Objects.equals(receiver, senderId))) {
                            ChatMessage message = doc.toObject(ChatMessage.class);
                            messageList.add(message);
                        }
                    }
                    messageAdapter.notifyDataSetChanged();
                    recyclerMessages.scrollToPosition(messageList.size() - 1);
                });
    }
}
