package com.example.bakis;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CreatedCourses extends Fragment {

    private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;
    public static final int REVIEW_EXERCISE_REQUEST = 3;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_createdcourses, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_test);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        context = getContext();

        ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        courseExerciseCrossRefViewModel = new ViewModelProvider(this).get(CourseExerciseCrossRefViewModel.class);
        courseExerciseCrossRefViewModel.getAllExercisesOfCourse(2).observe(getViewLifecycleOwner(), new Observer<List<CourseWithExercises>>() {
            @Override
            public void onChanged(List<CourseWithExercises> course) {
                adapter.setExercises(course.get(0).exercises);
            }
        });

        /*adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(context, ReviewExercise.class);
                intent.putExtra(ReviewExercise.EXTRA_TITLE, exercise.getTitle());
                intent.putExtra(ReviewExercise.EXTRA_DESCRIPTION, exercise.getDescription());
                intent.putExtra(ReviewExercise.EXTRA_GIF, exercise.getGif());
                startActivity(intent);
            }
        });*/
        return view;
    }
}
