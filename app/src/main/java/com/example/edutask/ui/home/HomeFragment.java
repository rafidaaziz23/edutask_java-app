package com.example.edutask.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.edutask.R;
import com.example.edutask.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Menampilkan "Selamat datang user"
        TextView welcomeTextView = binding.textWelcome;
        welcomeTextView.setText("Selamat datang user");

        // Menampilkan daftar quiz upcoming
        LinearLayout quizListLayout = binding.quizListLayout;

        // Contoh data quiz
        String[] quizTitles = {"Math Test", "Science Test","Programming Test", "Database Test"};
        String[] quizDeadlines = {"2025-01-20", "2025-01-25","2025-01-20", "2025-01-25"};

        // Menambahkan item quiz ke dalam LinearLayout
        for (int i = 0; i < quizTitles.length; i++) {
            View quizItemView = inflater.inflate(R.layout.quiz_item, quizListLayout, false);
            TextView quizTitleTextView = quizItemView.findViewById(R.id.text_quiz_title);
            TextView quizDeadlineTextView = quizItemView.findViewById(R.id.text_quiz_deadline);

            quizTitleTextView.setText(quizTitles[i]);
            quizDeadlineTextView.setText("Deadline: " + quizDeadlines[i]);

            quizListLayout.addView(quizItemView);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}