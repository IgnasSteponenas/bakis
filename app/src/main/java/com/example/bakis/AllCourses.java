package com.example.bakis;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllCourses extends Fragment {

    private CourseViewModel courseViewModel;
    private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;
    //public static final int REVIEW_EXERCISE_REQUEST = 3;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_allcourses, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_all_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        context = getContext();

        CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourses(courses);
            }
        });

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(context, ReviewCourse.class);
                intent.putExtra(ReviewCourse.EXTRA_COURSE_TITLE, course.getTitle());
                intent.putExtra(ReviewCourse.EXTRA_COURSE_ID, String.valueOf(course.getCourseId()));
                startActivity(intent);
            }
        });

        return view;
    }

}
