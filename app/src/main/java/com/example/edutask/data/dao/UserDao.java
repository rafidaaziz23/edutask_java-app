package com.example.edutask.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.edutask.data.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT COUNT(*) FROM users WHERE role = :role")
    int getUserCountByRole(String role);

    @Insert
    void insertUser(User user);

    @Query("SELECT COUNT(*) FROM users WHERE username = :username AND password = :password")
    int validateUser(String username, String password); // Tetap gunakan di thread terpisah

    @Query("SELECT COUNT(*) FROM users WHERE role = 'lecture'")
    int countLecturers();

    @Query("SELECT role FROM users WHERE username = :username LIMIT 1")
    String getUserRole(String username);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();
}
