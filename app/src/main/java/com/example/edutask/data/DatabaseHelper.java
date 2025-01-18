package com.example.edutask.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Nama database dan versi
    private static final String DATABASE_NAME = "education.db";
    private static final int DATABASE_VERSION = 1;

    // Konstruktor untuk DatabaseHelper
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel Users (Pengguna)
        String createUsersTable = "CREATE TABLE users ("
                + "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT NOT NULL, "
                + "email TEXT NOT NULL, "
                + "password TEXT NOT NULL, "
                + "role TEXT NOT NULL CHECK(role IN ('lecture', 'student')))";
        db.execSQL(createUsersTable);

        // Membuat tabel Quizzes (Kuis)
        String createQuizzesTable = "CREATE TABLE quizzes ("
                + "quiz_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL, "
                + "description TEXT, "
                + "deadline TIMESTAMP, "
                + "lecturer_id INTEGER, "
                + "FOREIGN KEY (lecturer_id) REFERENCES users(user_id))";
        db.execSQL(createQuizzesTable);

        // Membuat tabel Questions (Soal)
        String createQuestionsTable = "CREATE TABLE questions ("
                + "question_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "quiz_id INTEGER, "
                + "question_text TEXT NOT NULL, "
                + "answer_a TEXT NOT NULL, "
                + "answer_b TEXT NOT NULL, "
                + "answer_c TEXT NOT NULL, "
                + "answer_d TEXT NOT NULL, "
                + "correct_answer TEXT NOT NULL CHECK(correct_answer IN ('A', 'B', 'C', 'D')), "
                + "FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id))";
        db.execSQL(createQuestionsTable);

        // Membuat tabel Student Answers (Jawaban Siswa)
        String createStudentAnswersTable = "CREATE TABLE student_answers ("
                + "answer_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "student_id INTEGER, "
                + "question_id INTEGER, "
                + "selected_answer TEXT NOT NULL CHECK(selected_answer IN ('A', 'B', 'C', 'D')), "
                + "FOREIGN KEY (student_id) REFERENCES users(user_id), "
                + "FOREIGN KEY (question_id) REFERENCES questions(question_id))";
        db.execSQL(createStudentAnswersTable);

        // Membuat tabel Notifications (Notifikasi)
        String createNotificationsTable = "CREATE TABLE notifications ("
                + "notification_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "quiz_id INTEGER, "
                + "notification_time TIMESTAMP, "
                + "notification_sent INTEGER DEFAULT 0, "
                + "FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id))";
        db.execSQL(createNotificationsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Menghapus tabel lama jika versi database diperbarui
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS quizzes");
        db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS student_answers");
        db.execSQL("DROP TABLE IF EXISTS notifications");

        // Membuat tabel baru
        onCreate(db);
    }

    // CRUD

    //Login Register

    public boolean insertUser(String username, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("role", role);

        long result = db.insert("users", null, contentValues);
        return result != -1;
    }


    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }


    public void seedLecturer() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE role = 'lecture'", null);
        if (cursor.getCount() == 0) {
            // Jika belum ada, tambahkan user untuk lecturer dan student
            Log.d("Database", "Menambahkan user lecturer dan student");
            insertUser("lecturer", "lecturer@edutask.com", "lecturer123", "lecture");
            insertUser("student", "student@edutask.com", "student123", "student");
        } else {
            Log.d("Database", "User sudah ada, tidak perlu ditambahkan.");
        }
        cursor.close();
    }


}
