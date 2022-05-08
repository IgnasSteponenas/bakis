package com.example.bakis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses = new ArrayList<>();
    private List<CourseWithExercises> courseWithExercises = new ArrayList<>();
    //private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;
    private CourseViewModel courseViewModel;

    private CourseAdapter.OnItemClickListener listener;
    private Context context;

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

        //holder.starButton.setImageResource(R.drawable.ic_full_star);
        //holder.itemView.setOnClickListener(new View.OnClickListener()
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

    public CourseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public void setCourseViewModel(CourseViewModel courseViewModel) {
        this.courseViewModel = courseViewModel;
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
        private ImageButton starButton;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            exerciseNumber = itemView.findViewById(R.id.text_view_exercise_number);
            starButton = itemView.findViewById(R.id.image_button_star);

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
