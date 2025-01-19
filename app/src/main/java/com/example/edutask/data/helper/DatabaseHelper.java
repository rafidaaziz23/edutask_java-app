package com.example.edutask.data.helper;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.edutask.data.dao.UserDao;
import com.example.edutask.data.entity.Notification;
import com.example.edutask.data.entity.Question;
import com.example.edutask.data.entity.Quiz;
import com.example.edutask.data.entity.StudentAnswer;
import com.example.edutask.data.entity.User;
import com.example.edutask.data.database.AppDatabase;

// Anotasi untuk Room Database
public class DatabaseHelper {

    private static volatile DatabaseHelper instance;
    private static AppDatabase appDatabase;  // Ganti menjadi AppDatabase untuk akses database yang benar

    public static DatabaseHelper getInstance(final Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    appDatabase = AppDatabase.getInstance(context);  // Gunakan AppDatabase.getInstance()
                    instance = new DatabaseHelper();
                }
            }
        }
        return instance;
    }

    // Menambahkan method untuk akses UserDao
    public UserDao userDao() {
        return appDatabase.userDao();  // Gunakan userDao dari AppDatabase
    }

    public static void seedLecturer(Context context) {
        // Pastikan AppDatabase sudah benar-benar terinisialisasi
        AppDatabase database = AppDatabase.getInstance(context);

        new Thread(() -> {
            try {
                // Pastikan database sudah siap
                if (database != null) {
                    int lecturerCount = database.userDao().getUserCountByRole("lecture");
                    if (lecturerCount == 0) {
                        Log.d("DatabaseHelper", "Seeding lecturer and student data.");
                        // Lakukan insert user di sini
                        database.userDao().insertUser(new User("lecturer", "lecturer@edutask.com", "lecturer123", "lecture"));
                        database.userDao().insertUser(new User("student", "student@edutask.com", "student123", "student"));
                    } else {
                        Log.d("DatabaseHelper", "Users already exist, no seeding needed.");
                    }
                } else {
                    Log.e("DatabaseHelper", "Database instance is null");
                }
            } catch (Exception e) {
                Log.e("DatabaseHelper", "Error during seeding data", e);
            }
        }).start();
    }
}