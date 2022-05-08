package com.example.bakis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ReviewCourse extends AppCompatActivity {

    public static final String EXTRA_COURSE_TITLE =
            "com.example.bakis.EXTRA_COURSE_TITLE";

    public static final String EXTRA_COURSE_ID =
            "com.example.bakis.EXTRA_COURSE_ID";

    private TextView courseReviewTextTitle;

    private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_course);

        courseReviewTextTitle = findViewById(R.id.course_review_text_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        setTitle("Review course");

        courseReviewTextTitle.setText(intent.getStringExtra(EXTRA_COURSE_TITLE));


        RecyclerView recyclerView = findViewById(R.id.recycler_view_course_exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //context = getContext();

        ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        /*courseExerciseCrossRefViewModel = new ViewModelProvider(this).get(CourseExerciseCrossRefViewModel.class);
        courseExerciseCrossRefViewModel.getAllExercisesOfCourse(Integer.parseInt(intent.getStringExtra(EXTRA_COURSE_ID))).observe(this, new Observer<List<CourseWithExercises>>() {
            @Override
            public void onChanged(List<CourseWithExercises> course) {
                adapter.setExercises(course.get(0).exercises);
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}