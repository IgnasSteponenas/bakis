package com.example.bakis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CourseEndActivity extends AppCompatActivity {

    public static final String EXTRA_EXERCISES_ATTEMPTED =
            "com.example.bakis.EXTRA_EXERCISES_ATTEMPTED";
    public static final String EXTRA_COURSE_TIME_TAKEN =
            "com.example.bakis.EXTRA_COURSE_TIME_TAKEN";

    TextView completedExercises;
    TextView timeTaken;
    Button getBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_end);

        Intent intent;

        intent = getIntent();

        String count = intent.getStringExtra(EXTRA_EXERCISES_ATTEMPTED);
        String time = intent.getStringExtra(EXTRA_COURSE_TIME_TAKEN);

        completedExercises = findViewById(R.id.exercisesNumber);
        timeTaken = findViewById(R.id.timeNeeded);
        getBackButton = findViewById(R.id.getBackToMainActivity);

        completedExercises.setText(count);
        timeTaken.setText(time);

        getBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseEndActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}