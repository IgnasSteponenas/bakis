package com.example.bakis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class ReviewCourse extends AppCompatActivity {

    public static final String EXTRA_COURSE_TITLE =
            "com.example.bakis.EXTRA_COURSE_TITLE";

    public static final String EXTRA_COURSE_ID =
            "com.example.bakis.EXTRA_COURSE_ID";

    private TextView courseReviewTextTitle;
    private Button editCourseButton;
    private Button startCourseButton;

    private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;
    private CourseViewModel courseViewModel;
    private Course course = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_course);

        courseReviewTextTitle = findViewById(R.id.course_review_text_title);
        editCourseButton = findViewById(R.id.button_edit_course);
        startCourseButton = findViewById(R.id.button_start_course);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        courseExerciseCrossRefViewModel = new ViewModelProvider(this).get(CourseExerciseCrossRefViewModel.class);

        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra(EXTRA_COURSE_ID).toString());

        try {
            course = courseExerciseCrossRefViewModel.getAllExercisesOfCourse(id).course;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        setTitle(R.string.review_course);

        courseReviewTextTitle.setText(intent.getStringExtra(EXTRA_COURSE_TITLE));


        RecyclerView recyclerView = findViewById(R.id.recycler_view_course_exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //context = getContext();

        ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);



        /*CourseWithExercises courseWithExercises = null;

        try {
            courseWithExercises = courseExerciseCrossRefViewModel.getAllExercisesOfCourse(Integer.parseInt(intent.getStringExtra(EXTRA_COURSE_ID))).getValue();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        try {
            courseExerciseCrossRefViewModel.getAllExercisesOfCourseLiveData(Integer.parseInt(intent.getStringExtra(EXTRA_COURSE_ID))).observe(this, new Observer<CourseWithExercises>() {
                @Override
                public void onChanged(CourseWithExercises course) {
                    adapter.setExercises(course.exercises);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(ReviewCourse.this, ReviewExercise.class);

                if(Locale.getDefault().getLanguage().equals("lt") && exercise.getTitleInLithuanian() != null) {
                    intent.putExtra(ReviewExercise.EXTRA_TITLE, exercise.getTitleInLithuanian());
                    intent.putExtra(ReviewExercise.EXTRA_DESCRIPTION, exercise.getDescriptionInLithuanian());
                }else {
                    intent.putExtra(ReviewExercise.EXTRA_TITLE, exercise.getTitleInEnglish());
                    intent.putExtra(ReviewExercise.EXTRA_DESCRIPTION, exercise.getDescriptionInEnglish());
                }
                intent.putExtra(ReviewExercise.EXTRA_GIF, exercise.getGif());
                startActivity(intent);
            }
        });*/

        editCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewCourse.this, AddCourse.class);
                intent.putExtra(AddCourse.EXTRA_COURSE_ID, course.getCourseId());
                if(Locale.getDefault().getLanguage().equals("lt") && course.getTitleInLithuanian()!=null)
                    intent.putExtra(AddCourse.EXTRA_COURSE_TITLE, course.getTitleInLithuanian());
                else
                    intent.putExtra(AddCourse.EXTRA_COURSE_TITLE, course.getTitleInEnglish());
                intent.putExtra(AddCourse.EXTRA_CREATED_BY_USER, course.isCreatedByUser());
                intent.putExtra(AddCourse.EXTRA_LIKED, course.isLiked());
                startActivity(intent);
            }
        });

        startCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewCourse.this, StartCourseActivity.class);
                intent.putExtra(StartCourseActivity.EXTRA_COURSE_ID, course.getCourseId());
                /*if(Locale.getDefault().getLanguage().equals("lt") && course.getTitleInLithuanian()!=null)
                    intent.putExtra(StartCourseActivity.EXTRA_COURSE_TITLE, course.getTitleInLithuanian());
                else
                    intent.putExtra(StartCourseActivity.EXTRA_COURSE_TITLE, course.getTitleInEnglish());
                intent.putExtra(StartCourseActivity.EXTRA_CREATED_BY_USER, course.isCreatedByUser());
                intent.putExtra(StartCourseActivity.EXTRA_LIKED, course.isLiked());*/
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}