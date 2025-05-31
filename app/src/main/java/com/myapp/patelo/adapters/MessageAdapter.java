package com.myapp.patelo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.patelo.R;
import com.myapp.patelo.models.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SENT = 1;
    private static final int TYPE_RECEIVED = 2;

    private final Context context;
    private final List<ChatMessage> messageList;
    private final String currentUserId;

    public MessageAdapter(Context context, List<ChatMessage> messageList, String currentUserId) {
        this.context = context;
        this.messageList = messageList;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messageList.get(position);
        return message.getSenderId().equals(currentUserId) ? TYPE_SENT : TYPE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sent_message, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_received_message, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        String formattedTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(message.getTimestamp());

        if (holder.getItemViewType() == TYPE_SENT) {
            ((SentViewHolder) holder).msg.setText(message.getMessage());
            ((SentViewHolder) holder).time.setText(formattedTime);
        } else {
            ((ReceivedViewHolder) holder).msg.setText(message.getMessage());
            ((ReceivedViewHolder) holder).time.setText(formattedTime);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView msg, time;
        SentViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.textSentMessage);
            time = itemView.findViewById(R.id.textSentTime);
        }
    }

    static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView msg, time;
        ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.textReceivedMessage);
            time = itemView.findViewById(R.id.textReceivedTime);
        }
    }
}
