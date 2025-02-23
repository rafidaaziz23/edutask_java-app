package com.example.edutask.ui.dashboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.edutask.R;
import com.example.edutask.data.entity.Quiz;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private List<Quiz> quizList;

    public QuizAdapter(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);
        holder.tvQuizTitle.setText("Title: " + quiz.getTitle());
        holder.tvQuizDescription.setText("Description: " + quiz.getDescription());
        holder.tvQuizDeadline.setText("Deadline: " + quiz.getDeadline());
    }

    @Override
    public int getItemCount() {
        return quizList != null ? quizList.size() : 0;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizList = quizzes;
        notifyDataSetChanged();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuizTitle, tvQuizDescription, tvQuizDeadline;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuizTitle = itemView.findViewById(R.id.tv_quiz_title);
            tvQuizDescription = itemView.findViewById(R.id.tv_quiz_description);
            tvQuizDeadline = itemView.findViewById(R.id.tv_quiz_deadline);
        }
    }
}
