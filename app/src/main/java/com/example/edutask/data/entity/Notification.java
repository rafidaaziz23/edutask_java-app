package com.example.edutask.data.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    public int notification_id;

    public int quiz_id;
    public String notification_time;
    public boolean notification_sent;

    // Konstruktor untuk Room
    public Notification(int quiz_id, String notification_time, boolean notification_sent) {
        this.quiz_id = quiz_id;
        this.notification_time = notification_time;
        this.notification_sent = notification_sent;
    }

    // Konstruktor lain yang tidak digunakan oleh Room
    @Ignore
    public Notification(int notification_id, int quiz_id, String notification_time, boolean notification_sent) {
        this.notification_id = notification_id;
        this.quiz_id = quiz_id;
        this.notification_time = notification_time;
        this.notification_sent = notification_sent;
    }

    public Notification() {

    }
}
