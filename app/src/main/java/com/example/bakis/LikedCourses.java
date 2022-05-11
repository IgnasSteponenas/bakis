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

public class LikedCourses extends Fragment {

    private CourseViewModel courseViewModel;
    private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likedcourses, container, false);

        getActivity().setTitle(R.string.my_liked_courses);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_all_liked_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        context = getContext();

        CourseAdapter adapter = new CourseAdapter(context);
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseExerciseCrossRefViewModel = new ViewModelProvider(this).get(CourseExerciseCrossRefViewModel.class);
        adapter.setCourseViewModel(courseViewModel);
        adapter.setCourseExerciseCrossRefViewModel(courseExerciseCrossRefViewModel);

        courseViewModel.getAllLikedCourses().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> likedCourses) {
                adapter.setCourses(likedCourses);

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
