package com.example.edutask.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.edutask.data.entity.Notification;

import java.util.List;

@Dao
public interface NotificationDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotification(Notification notification);

    @Query("SELECT * FROM notifications WHERE quiz_id = :quizId")
    List<Notification> getNotificationsByQuizId(int quizId);

    @Query("SELECT * FROM notifications WHERE notification_sent = 0")
    List<Notification> getPendingNotifications();

    @Query("SELECT * FROM notifications order by notification_id desc")
    List<Notification> getAllNotifications();
}
