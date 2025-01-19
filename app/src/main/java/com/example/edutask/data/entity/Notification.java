package com.example.edutask.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    public int notification_id;

    public int quiz_id;             // Foreign key ke `quizzes`
    public String notification_time; // Simpan dalam format string ISO-8601
    public boolean notification_sent; // True jika notifikasi sudah terkirim
}
