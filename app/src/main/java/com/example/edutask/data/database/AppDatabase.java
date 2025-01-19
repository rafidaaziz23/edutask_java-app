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

@Database(entities = {User.class, Quiz.class, Question.class, StudentAnswer.class, Notification.class}, version = 2, exportSchema = false)
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
                    .addMigrations(MIGRATION_1_2)  // Menambahkan migrasi
                    .build();
        }
        return instance;
    }

    // Migrasi dari versi 1 ke versi 2 (contoh)
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Misalnya, menambahkan kolom baru 'new_column' pada tabel 'users'
            database.execSQL("ALTER TABLE users ADD COLUMN new_column INTEGER DEFAULT 0");
        }
    };
}


