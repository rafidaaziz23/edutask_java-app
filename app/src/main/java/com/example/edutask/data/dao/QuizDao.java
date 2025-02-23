package com.example.edutask.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.edutask.data.entity.Quiz;

import java.util.List;

@Dao
public interface QuizDao {

    @Insert
    void insertQuiz(Quiz quiz);

    @Query("SELECT * FROM quizzes WHERE lecturerId = :lecturerId") // Updated to match the new field name
    List<Quiz> getQuizzesByLecturer(int lecturerId);

    @Query("SELECT * FROM quizzes")
    List<Quiz> getAllQuizzes();
}