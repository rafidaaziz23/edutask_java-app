package com.example.edutask.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.edutask.R;
import com.example.edutask.data.entity.Quiz;
import com.example.edutask.ui.dashboard.adapter.QuizAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_quiz);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        quizAdapter = new QuizAdapter(new ArrayList<>());
        recyclerView.setAdapter(quizAdapter);

        loadSampleQuizzes();

        return view;
    }

    private void loadSampleQuizzes() {
        List<Quiz> sampleQuizzes = new ArrayList<>();
        sampleQuizzes.add(new Quiz("Math Quiz", "Basic Algebra Questions", "What is 2+2?", "4", "2025-01-31", 1));
        sampleQuizzes.add(new Quiz("Physics Quiz", "Newton's Laws", "What is F=ma?", "Force Equation", "2025-02-10", 2));

        quizAdapter.setQuizzes(sampleQuizzes);
    }
}
