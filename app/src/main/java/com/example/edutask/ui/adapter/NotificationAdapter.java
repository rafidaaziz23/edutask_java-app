package com.example.edutask.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edutask.R;
import com.example.edutask.data.entity.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNotificationTime;
        private final TextView tvNotificationStatus;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNotificationTime = itemView.findViewById(R.id.tv_notification_time);
            tvNotificationStatus = itemView.findViewById(R.id.tv_notification_status);
        }

        public void bind(Notification notification) {
            tvNotificationTime.setText("Time: " + notification.notification_time);
            tvNotificationStatus.setText("Status: " + (notification.notification_sent ? "Sent" : "Not Sent"));
        }
    }
}