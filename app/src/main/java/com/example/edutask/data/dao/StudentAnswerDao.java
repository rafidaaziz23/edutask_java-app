package com.example.edutask.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.edutask.data.entity.StudentAnswer;

import java.util.List;

@Dao
public interface StudentAnswerDao {

    @Insert
    void insertAnswer(StudentAnswer answer);

    @Query("SELECT * FROM student_answers WHERE student_id = :studentId AND question_id = :questionId")
    StudentAnswer getAnswerByStudentAndQuestion(int studentId, int questionId);
}
