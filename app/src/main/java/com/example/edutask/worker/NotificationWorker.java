package com.example.edutask.worker;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.edutask.R;
import com.example.edutask.data.database.AppDatabase;
import com.example.edutask.data.entity.Notification;

import java.util.List;

public class NotificationWorker extends Worker {

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Dapatkan instance database
        AppDatabase database = AppDatabase.getInstance(getApplicationContext());

        // Dapatkan semua notifikasi yang belum terkirim
        List<Notification> pendingNotifications = database.notificationDao().getPendingNotifications();

        for (Notification notification : pendingNotifications) {
            // Kirim notifikasi
            sendNotification(notification);

            // Tandai notifikasi sebagai terkirim
            notification.notification_sent = true;
            database.notificationDao().insertNotification(notification);
        }

        return Result.success();
    }

    @SuppressLint("NotificationPermission")
    private void sendNotification(Notification notification) {
        // Buat channel notifikasi (untuk Android Oreo ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "quiz_notification_channel",
                    "Quiz Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        // Buat notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "quiz_notification_channel")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Quiz Reminder")
                .setContentText("You have a quiz coming up!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Tampilkan notifikasi
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notification.notification_id, builder.build());
    }
}