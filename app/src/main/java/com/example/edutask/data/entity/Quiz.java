package com.example.edutask.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "quizzes")
public class Quiz {
    @PrimaryKey(autoGenerate = true)
    private int quizId; // Changed from quiz_id to quizId
    private String title;
    private String description;
    private String deadline;
    private int lecturerId; // Changed from lecturer_id to lecturerId
    private String question;
    private String answer;

    public Quiz(String title, String description, String question, String answer, String deadline, int lecturerId) {
        this.title = title;
        this.description = description;
        this.question = question;
        this.answer = answer;
        this.deadline = deadline;
        this.lecturerId = lecturerId; // Changed from lecturer_id to lecturerId
    }

    // Getters and Setters
    public int getQuizId() {
        return quizId; // Updated to match the new field name
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId; // Updated to match the new field name
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getLecturerId() {
        return lecturerId; // Updated to match the new field name
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId; // Updated to match the new field name
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer; // Added getter for answer
    }

    public void setAnswer(String answer) { // Added setter for answer
        this.answer = answer;
    }
}