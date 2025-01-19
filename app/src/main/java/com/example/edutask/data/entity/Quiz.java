package com.example.edutask.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quizzes")
public class Quiz {
    @PrimaryKey(autoGenerate = true)
    public int quiz_id;

    public String title;
    public String description;
    public String deadline; // Simpan dalam format string ISO-8601
    public int lecturer_id;  // Foreign key ke `users`
}
