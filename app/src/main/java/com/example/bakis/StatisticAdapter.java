package com.example.bakis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import pl.droidsonroids.gif.GifImageView;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticHolder> {

    private List<Statistic> statistics = new ArrayList<>();
    private CourseViewModel courseViewModel;
    //private List<Exercise> exercises = new ArrayList<>();
    /*private List<Integer> exerciseIds = new ArrayList<>();
    private List<Integer> exerciseRepeats = new ArrayList<>();
    private List<Integer> exerciseTimeTaken = new ArrayList<>();
    private String courseName;*/

    @NonNull
    @Override
    public StatisticHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.statistic_item, parent, false);

        return new StatisticHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticHolder holder, int position) {
        Statistic statistic = statistics.get(position);
        //courseViewModel.get
        Course course = null;
        try {
            course = courseViewModel.getCourseById(statistic.getCourseAttemptedId());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean[] completed = statistic.getCompletedExercises();
        int completedCount = 0;

        for (int i=0; i<completed.length; i++) {
            if (completed[i]){
                completedCount++;
            }
        }
        String stringExercisesDone = completedCount + "/" + completed.length;

        if (Locale.getDefault().getLanguage().equals("lt") && course.getTitleInLithuanian() != null) {
            holder.textViewTitle.setText(course.getTitleInLithuanian());
        } else {
            holder.textViewTitle.setText(course.getTitleInEnglish());
        }

        holder.textViewExercisesDone.setText(stringExercisesDone);

        int minutes = (int) statistic.getCourseTimeTaken() / 60;
        int seconds = (int) statistic.getCourseTimeTaken() % 60;

        String timeTakenString;
        timeTakenString = "" + minutes;
        timeTakenString += ":";
        if(seconds < 10) timeTakenString+= "0";
        timeTakenString += seconds;

        holder.textViewTimeTaken.setText(timeTakenString);
        //Exercise currentExercise = exercises.get(position);
        /*int id = exerciseIds.get(position);
        int repeats = exerciseRepeats.get(position);




        holder.textViewRepetitionCount.setText(repeats);
        holder.textViewCourseTitle.setText(courseName);*/
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }

    /*public void setExerciseIds(List<Integer> exerciseIds) {
        this.exerciseIds = exerciseIds;
    }

    public void setExerciseRepeats(List<Integer> exerciseRepeats) {
        this.exerciseRepeats = exerciseRepeats;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
*/
    public void setStatistics(List<Statistic> statistics) {
        this.statistics = statistics;
        notifyDataSetChanged();
    }

    public void setCourseViewModel(CourseViewModel courseViewModel) {
        this.courseViewModel = courseViewModel;
    }


    class StatisticHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewExercisesDone;
        private TextView textViewTimeTaken;

        public StatisticHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewExercisesDone = itemView.findViewById(R.id.text_view_exercises_done);
            textViewTimeTaken = itemView.findViewById(R.id.text_view_exercise_time_taken);

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
