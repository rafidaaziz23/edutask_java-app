package com.example.edutask.ui.dashboard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edutask.R;
import com.example.edutask.data.database.AppDatabase;
import com.example.edutask.data.entity.Notification;
import com.example.edutask.data.entity.Quiz;
import com.example.edutask.ui.adapter.QuizAdapter;
import com.example.edutask.worker.NotificationWorker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_quiz);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        quizAdapter = new QuizAdapter(new ArrayList<>());
        recyclerView.setAdapter(quizAdapter);

        // Load sample quizzes or from database
        loadQuizzes();

        // Temukan tombol "Create Quiz"
        Button btnCreateQuiz = view.findViewById(R.id.btn_create_quiz);

        // Atur onClickListener untuk tombol
        btnCreateQuiz.setOnClickListener(v -> showCreateQuizDialog());

        return view;
    }

    private void loadQuizzes() {
        // Dapatkan instance database
        AppDatabase database = AppDatabase.getInstance(requireContext());

        // Jalankan operasi database di background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Quiz> quizzes = database.quizDao().getAllQuizzes();
            requireActivity().runOnUiThread(() -> quizAdapter.setQuizzes(quizzes));
        });
    }

    // Method untuk menampilkan dialog form Create Quiz
    private void showCreateQuizDialog() {
        // Inflate layout form
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_create_quiz, null);

        // Temukan input fields
        EditText etQuizTitle = dialogView.findViewById(R.id.et_quiz_title);
        EditText etQuizDescription = dialogView.findViewById(R.id.et_quiz_description);
        EditText etQuizDeadline = dialogView.findViewById(R.id.et_quiz_deadline);
        EditText etQuestion = dialogView.findViewById(R.id.et_question);
        EditText etAnswer = dialogView.findViewById(R.id.et_answer);

        // Inisialisasi Calendar
        final Calendar calendar = Calendar.getInstance();

        // Tambahkan onClickListener ke etQuizDeadline untuk menampilkan DatePicker
        etQuizDeadline.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        etQuizDeadline.setText(dateFormat.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        // Buat AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView)
                .setTitle("Create Quiz")
                .setPositiveButton("Create", (dialog, which) -> {
                    // Ambil input dari form
                    String title = etQuizTitle.getText().toString();
                    String description = etQuizDescription.getText().toString();
                    String deadline = etQuizDeadline.getText().toString();
                    String question = etQuestion.getText().toString();
                    String answer = etAnswer.getText().toString();

                    // Lakukan sesuatu dengan data yang diinput (misalnya, simpan ke database)
                    saveQuiz(title, description, deadline, question, answer);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Tampilkan dialog
        builder.create().show();
    }

    // Method untuk menyimpan quiz ke database
    private void saveQuiz(String title, String description, String deadline, String question, String answer) {
        // Buat objek Quiz
        Quiz quiz = new Quiz(title, description, question, answer, deadline, 2);

        // Dapatkan instance database
        AppDatabase database = AppDatabase.getInstance(requireContext());

        // Jalankan operasi database di background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            // Simpan quiz ke database dan dapatkan quiz_id yang baru di-generate
            long quizId = database.quizDao().insertQuiz(quiz);

            // Buat notifikasi untuk quiz yang baru dibuat
            Notification notification = new Notification();
            notification.quiz_id = (int) quizId; // Gunakan quiz_id yang baru di-generate
            notification.notification_time = deadline; // Gunakan deadline quiz sebagai waktu notifikasi
            notification.notification_sent = false; // Notifikasi belum terkirim

            // Simpan notifikasi ke database
            database.notificationDao().insertNotification(notification);

            // Jadwalkan Worker untuk mengirim notifikasi pada waktu yang sesuai
            scheduleNotificationWorker(deadline);

            // Perbarui UI di main thread
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Quiz saved successfully!", Toast.LENGTH_SHORT).show();
                loadQuizzes(); // Reload quizzes setelah menyimpan quiz baru
            });
        });
    }

    // Method untuk menjadwalkan Worker
    private void scheduleNotificationWorker(String deadline) {
        // Parse deadline dari string ke Calendar
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Calendar deadlineCalendar = Calendar.getInstance();
        try {
            deadlineCalendar.setTime(dateFormat.parse(deadline));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Hitung delay dalam milidetik (waktu sekarang hingga deadline)
        long delay = deadlineCalendar.getTimeInMillis() - System.currentTimeMillis();

        // Buat OneTimeWorkRequest dengan delay
        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build();

        // Jadwalkan Worker
        WorkManager.getInstance(requireContext()).enqueue(notificationWork);
    }
}