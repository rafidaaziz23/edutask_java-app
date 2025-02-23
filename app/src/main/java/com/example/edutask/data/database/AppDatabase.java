package com.example.edutask.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.edutask.data.dao.UserDao;
import com.example.edutask.data.dao.QuizDao;
import com.example.edutask.data.dao.QuestionDao;
import com.example.edutask.data.dao.StudentAnswerDao;
import com.example.edutask.data.dao.NotificationDao;
import com.example.edutask.data.entity.User;
import com.example.edutask.data.entity.Quiz;
import com.example.edutask.data.entity.Question;
import com.example.edutask.data.entity.StudentAnswer;
import com.example.edutask.data.entity.Notification;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Quiz.class, Question.class, StudentAnswer.class, Notification.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    // DAO accessors
    public abstract UserDao userDao();
    public abstract QuizDao quizDao();
    public abstract QuestionDao questionDao();
    public abstract StudentAnswerDao studentAnswerDao();
    public abstract NotificationDao notificationDao();

    // Singleton instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "education.db")
                    .fallbackToDestructiveMigration()  // Ensure you add MIGRATION_2_3
                    .build();
        }
        return instance;
    }

    // Migration from version 1 to 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users ADD COLUMN new_column INTEGER DEFAULT 0");
        }
    };

    // Migration from version 2 to 3
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Add the new column to the quizzes table
            database.execSQL("ALTER TABLE quizzes ADD COLUMN question TEXT");
            // If you have more columns to add, add them here as well
        }
    };
}




