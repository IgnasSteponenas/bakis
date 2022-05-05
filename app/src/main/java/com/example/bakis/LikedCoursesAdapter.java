package com.example.bakis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LikedCoursesAdapter extends RecyclerView.Adapter<LikedCoursesAdapter.LikedCoursesHolder>{

    private List<Course> likedCourses = new ArrayList<>();

    @NonNull
    @Override
    public LikedCoursesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);

        return new LikedCoursesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedCoursesHolder holder, int position) {
        Course currentCourse = likedCourses.get(position);
        holder.textViewTitle.setText(currentCourse.getTitle());
        //holder.exerciseNumber.setText(courseWithExercises.get(position).exercises.size());
    }

    @Override
    public int getItemCount() {
        return likedCourses.size();
    }

    public void setLikedCourses(List<Course> likedCourses) {
        this.likedCourses = likedCourses;
        notifyDataSetChanged();
    }

    class LikedCoursesHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView exerciseNumber;

        public LikedCoursesHolder(@NonNull View itemView) {
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
