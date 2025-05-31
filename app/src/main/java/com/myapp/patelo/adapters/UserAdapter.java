package com.myapp.patelo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.patelo.R;
import com.myapp.patelo.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> userList;
    private final Context context;

    public UserAdapter(List<User> users, Context ctx) {
        this.userList = users;
        this.context = ctx;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_user_card, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User u = userList.get(position);
        holder.nameText.setText(u.getFullName());
        holder.emailText.setText(u.getEmail());
        holder.typeText.setText(u.getAccountType());

        holder.itemView.setOnClickListener(v -> Toast.makeText(context, "Clicked: " + u.getFullName(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, emailText, typeText;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.txtFullName);
            emailText = itemView.findViewById(R.id.txtEmail);
            typeText = itemView.findViewById(R.id.txtAccountType);
        }
    }
}
