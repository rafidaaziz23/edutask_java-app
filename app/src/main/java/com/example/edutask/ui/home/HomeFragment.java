package com.example.edutask.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.edutask.R;
import com.example.edutask.data.database.AppDatabase;
import com.example.edutask.data.entity.Quiz;
import com.example.edutask.databinding.FragmentHomeBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Menampilkan "Selamat datang user"
        TextView welcomeTextView = binding.textWelcome;
        welcomeTextView.setText("Selamat datang user");

        // Menampilkan daftar quiz upcoming
        LinearLayout quizListLayout = binding.quizListLayout;

        // Ambil data quiz dari database
        loadQuizzesFromDatabase(inflater, quizListLayout);

        return root;
    }

    private void loadQuizzesFromDatabase(LayoutInflater inflater, LinearLayout quizListLayout) {
        // Dapatkan instance database
        AppDatabase database = AppDatabase.getInstance(requireContext());

        // Jalankan operasi database di background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            // Ambil semua quiz dari database
            List<Quiz> quizzes = database.quizDao().getAllQuizzes();

            // Update UI di main thread
            requireActivity().runOnUiThread(() -> {
                // Bersihkan layout sebelum menambahkan data baru
                quizListLayout.removeAllViews();

                // Tambahkan item quiz ke dalam LinearLayout
                for (Quiz quiz : quizzes) {
                    View quizItemView = inflater.inflate(R.layout.quiz_item, quizListLayout, false);
                    TextView quizTitleTextView = quizItemView.findViewById(R.id.text_quiz_title);
                    TextView quizDeadlineTextView = quizItemView.findViewById(R.id.text_quiz_deadline);

                    quizTitleTextView.setText(quiz.getTitle());
                    quizDeadlineTextView.setText("Deadline: " + quiz.getDeadline());

                    quizListLayout.addView(quizItemView);
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}