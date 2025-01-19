package com.example.edutask.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class Question {
    @PrimaryKey(autoGenerate = true)
    public int question_id;

    public int quiz_id; // Foreign key ke `quizzes`
    public String question_text;
    public String answer_a;
    public String answer_b;
    public String answer_c;
    public String answer_d;
    public String correct_answer; // Simpan nilai A, B, C, atau D
}
