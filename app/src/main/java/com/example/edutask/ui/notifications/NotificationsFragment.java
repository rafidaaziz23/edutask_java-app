package com.example.edutask.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edutask.R;
import com.example.edutask.data.database.AppDatabase;
import com.example.edutask.data.entity.Notification;
import com.example.edutask.ui.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi Adapter
        notificationAdapter = new NotificationAdapter(new ArrayList<>());
        recyclerView.setAdapter(notificationAdapter);

        // Load data dari database
        loadNotifications();

        return view;
    }

    private void loadNotifications() {
        // Dapatkan instance database
        AppDatabase database = AppDatabase.getInstance(requireContext());

        // Jalankan operasi database di background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Notification> notifications = database.notificationDao().getAllNotifications();
            requireActivity().runOnUiThread(() -> notificationAdapter.setNotifications(notifications));
        });
    }
}