package com.example.bakis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses = new ArrayList<>();
    private List<CourseWithExercises> courseWithExercises = new ArrayList<>();
    private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;

    private CourseAdapter.OnItemClickListener listener;

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
        //CourseWithExercises currentCourseWithExercises = courseExerciseCrossRefViewModel.getAllExercisesOfCourse(position).getValue().get(0);
        //CourseWithExercises currentCourseWithExercises = courseWithExercises.get(position);
        holder.textViewTitle.setText(currentCourse.getTitle());
        //holder.exerciseNumber.setText(currentCourseWithExercises.exercises.size());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    /*public void setCoursesWithExercises(List<CourseWithExercises> courseWithExercises) {
        this.courseWithExercises = courseWithExercises;
        notifyDataSetChanged();
    }*/

    public Course getCourseAt(int position) {
        return courses.get(position);
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView exerciseNumber;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            exerciseNumber = itemView.findViewById(R.id.text_view_exercise_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
