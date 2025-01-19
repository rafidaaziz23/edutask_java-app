package com.example.edutask.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_answers")
public class StudentAnswer {
    @PrimaryKey(autoGenerate = true)
    public int answer_id;

    public int student_id;  // Foreign key ke `users`
    public int question_id; // Foreign key ke `questions`
    public String selected_answer; // Nilai A, B, C, atau D
}
