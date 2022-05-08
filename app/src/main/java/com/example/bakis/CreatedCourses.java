package com.example.bakis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CreatedCourses extends Fragment {

    private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;
    private CourseViewModel courseViewModel;
    public static final int ADD_COURSE_REQUEST = 6;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //button_add_course

        View view = inflater.inflate(R.layout.fragment_createdcourses, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_created_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        context = getContext();

        FloatingActionButton buttonAddExercise = view.findViewById(R.id.button_add_course);
        buttonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddCourse.class);
                startActivity(intent);
            }
        });

        CourseAdapter adapter = new CourseAdapter(context);
        recyclerView.setAdapter(adapter);

        courseExerciseCrossRefViewModel = new ViewModelProvider(this).get(CourseExerciseCrossRefViewModel.class);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        adapter.setCourseViewModel(courseViewModel);

        courseViewModel.getUserCreatedCourses().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> userCreatedCourses) {
                adapter.setCourses(userCreatedCourses);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course course = adapter.getCourseAt(viewHolder.getAdapterPosition());

                courseExerciseCrossRefViewModel.deleteByCourseId(course.getCourseId());
                courseViewModel.delete(course);

                Toast.makeText(context, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(context, AddCourse.class);
                intent.putExtra(AddCourse.EXTRA_COURSE_ID, course.getCourseId());
                intent.putExtra(AddCourse.EXTRA_COURSE_TITLE, course.getTitle());
                intent.putExtra(AddCourse.EXTRA_CREATED_BY_USER, course.isCreatedByUser());
                intent.putExtra(AddCourse.EXTRA_LIKED, course.isLiked());
                startActivity(intent);
            }
        });

        return view;
    }
}
