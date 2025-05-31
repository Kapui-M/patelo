package com.myapp.patelo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.myapp.patelo.R;
import com.myapp.patelo.models.Event;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;

    public EventAdapter(List<Event> events) {
        this.eventList = events;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc, date;
        public ImageView image;

        public EventViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.cardEventTitle);
            desc = v.findViewById(R.id.cardEventDesc);
            date = v.findViewById(R.id.cardEventDate);
            image = v.findViewById(R.id.cardEventImage);
        }
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event e = eventList.get(position);
        holder.title.setText(e.getTitle());
        holder.desc.setText(e.getDescription());
        holder.date.setText(e.getDate());
        Glide.with(holder.image.getContext()).load(e.getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}

