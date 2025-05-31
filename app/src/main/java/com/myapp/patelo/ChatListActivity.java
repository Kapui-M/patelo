package com.myapp.patelo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import com.myapp.patelo.adapters.ChatListAdapter;
import com.myapp.patelo.models.ChatMessage;
import com.myapp.patelo.models.User;

import java.util.*;

public class ChatListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<User> userList;
    private ChatListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.chatListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatListAdapter(userList, this::onUserClicked);
        recyclerView.setAdapter(adapter);

        fetchChatUsers();
    }

    private void fetchChatUsers() {
        String currentUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        db.collection("messages")
                .whereEqualTo("senderId", currentUserId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Set<String> contactIds = new HashSet<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        contactIds.add(doc.getString("receiverId"));
                    }
                    loadUserDetails(contactIds);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error loading chats", Toast.LENGTH_SHORT).show());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadUserDetails(Set<String> userIds) {
        for (String id : userIds) {
            db.collection("users").document(id)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            userList.add(user);
                            adapter.notifyDataSetChanged();
                        }
                    });
        }
    }

    private void onUserClicked(User user) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("receiverId", user.getUid());
        startActivity(intent);
    }
}
