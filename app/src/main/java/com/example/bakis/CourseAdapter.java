package com.example.bakis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses = new ArrayList<>();
    private List<CourseWithExercises> courseWithExercises = new ArrayList<>();

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);

        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course currentCourse = courses.get(position);
        holder.textViewTitle.setText(currentCourse.getTitle());
        //holder.exerciseNumber.setText(courseWithExercises.get(position).exercises.size());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView exerciseNumber;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            exerciseNumber = itemView.findViewById(R.id.text_view_exercise_number);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(exercises.get(position));
                    }
                }
            });*/
        }
    }
}
