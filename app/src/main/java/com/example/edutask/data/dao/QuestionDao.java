package com.example.edutask.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.edutask.data.entity.Question;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert
    void insertQuestion(Question question);

    @Query("SELECT * FROM questions WHERE quiz_id = :quizId")
    List<Question> getQuestionsByQuizId(int quizId);
}
