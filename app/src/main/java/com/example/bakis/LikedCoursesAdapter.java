package com.example.bakis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LikedCoursesAdapter extends RecyclerView.Adapter<LikedCoursesAdapter.LikedCoursesHolder>{

    private List<Course> likedCourses = new ArrayList<>();
    private CourseViewModel courseViewModel;

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

        holder.starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCourse.isLiked()) {
                    //removeHighlightView(holder);
                    holder.starButton.setImageResource(R.drawable.ic_star_outline);
                    currentCourse.setLiked(false);
                    courseViewModel.update(currentCourse);
                }else{
                    //addHighlightView(holder);
                    holder.starButton.setImageResource(R.drawable.ic_full_star);
                    currentCourse.setLiked(true);
                    courseViewModel.update(currentCourse);
                }
            }
        });

        if (currentCourse.isLiked())
            holder.starButton.setImageResource(R.drawable.ic_full_star);
        else
            holder.starButton.setImageResource(R.drawable.ic_star_outline);

    }

    @Override
    public int getItemCount() {
        return likedCourses.size();
    }

    public void setLikedCourses(List<Course> likedCourses) {
        this.likedCourses = likedCourses;
        notifyDataSetChanged();
    }

    public void setCourseViewModel(CourseViewModel courseViewModel) {
        this.courseViewModel = courseViewModel;
    }

    class LikedCoursesHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView exerciseNumber;
        private ImageButton starButton;

        public LikedCoursesHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            exerciseNumber = itemView.findViewById(R.id.text_view_exercise_number);
            starButton = itemView.findViewById(R.id.image_button_star);
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
