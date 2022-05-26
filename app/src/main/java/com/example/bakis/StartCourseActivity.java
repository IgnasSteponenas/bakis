package com.example.bakis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import pl.droidsonroids.gif.GifImageView;

public class StartCourseActivity extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID =
            "com.example.bakis.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_TITLE =
            "com.example.bakis.EXTRA_COURSE_TITLE";
    public static final String EXTRA_CREATED_BY_USER =
            "com.example.bakis.EXTRA_CREATED_BY_USER";
    public static final String EXTRA_LIKED =
            "com.example.bakis.EXTRA_LIKED";

    TextView exerciseTitle;
    TextView currentExercise;
    GifImageView exerciseImage;
    TextView timeRemaining;
    TextView repeatsRemaining;
    TextView infoText;
    ImageButton infoButton;
    ImageButton settingButton;
    ImageButton stopStartButton;
    ImageButton nextExerciseButton;
    ImageButton previousExercise;


    CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;
    StatisticViewModel statisticViewModel;

    Intent intent;
    int courseId = -1;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    TextView exerciseDescription;
    ImageButton closeDialog;

    List<Exercise> exercises = null;

    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 600000; // 10 mins
    private boolean timeRunning = false;

    int timePerRepeat;
    int repeats;
    int overallTime;
    int timeToChangeRepeatCount;
    int remainingRepeatsCount;

    int currentExerciseIndex;

    Statistic statistic;
    private int courseTimeTaken;
    private int courseAttemptedId;
    private List<Integer> exercisesAttemptedIds = new ArrayList<>();
    private List<Integer> exercisesRepetitionsAttempted = new ArrayList<>();
    private List<Boolean> exercisesCompleted = new ArrayList<>();

    private Calendar calendar;
    SimpleDateFormat dateFormat;
    String dateString;
    Date sqliteDate;
    int completedExercises = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_course);

        setTitle(R.string.course_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        exerciseTitle = findViewById(R.id.exercise_title_start_course);
        currentExercise = findViewById(R.id.current_exercise_start_course);
        exerciseImage = findViewById(R.id.gif_image_view_start_course);
        timeRemaining = findViewById(R.id.time_remaining_text);
        repeatsRemaining = findViewById(R.id.repeats_remaining_text);
        infoText = findViewById(R.id.info_text);
        infoButton = findViewById(R.id.info_button);
        settingButton = findViewById(R.id.settings_button);
        stopStartButton = findViewById(R.id.stop_play_button);
        nextExerciseButton = findViewById(R.id.nextExercise);
        previousExercise = findViewById(R.id.previousExercise);

        infoText.setText(R.string.pause);

        intent = getIntent();
        courseExerciseCrossRefViewModel = new ViewModelProvider(this).get(CourseExerciseCrossRefViewModel.class);
        statisticViewModel = new ViewModelProvider(this).get(StatisticViewModel.class);

        courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);

        if(courseId != -1) {
            try {
                exercises = courseExerciseCrossRefViewModel.getAllExercisesOfCourse(courseId).exercises;
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        //statistics
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateString = dateFormat.format(calendar.getTime());
        try {
            sqliteDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //System.out.println("/////////////data: " + date);

        currentExerciseIndex = 0;

        prePopulateStatisticParameters();

        changeExercise(currentExerciseIndex);

        if(currentExerciseIndex == 0)
            previousExercise.setVisibility(View.GONE);

        nextExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentExerciseIndex < exercises.size()-1) {
                    currentExerciseIndex++;
                    changeExercise(currentExerciseIndex);

                    previousExerciseVisibility();
                }else if (currentExerciseIndex == exercises.size()-1){
                    changeToEndWindow();
                }
            }
        });

        previousExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentExerciseIndex > 0) {
                    currentExerciseIndex--;
                    changeExercise(currentExerciseIndex);

                    previousExerciseVisibility();
                }
            }
        });

        stopStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopTimer();
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Locale.getDefault().getLanguage().equals("lt") && !exercises.get(currentExerciseIndex).isCreatedByUser())
                    createPopupWindowForExerciseDescription(exercises.get(currentExerciseIndex).getDescriptionInLithuanian());
                else
                    createPopupWindowForExerciseDescription(exercises.get(currentExerciseIndex).getDescriptionInEnglish());
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //settings popup
            }
        });
    }

    public void previousExerciseVisibility(){
        if(currentExerciseIndex == 0)
            previousExercise.setVisibility(View.GONE);
        else
            previousExercise.setVisibility(View.VISIBLE);
    }

    public void startStopTimer(){
        if(timeRunning){
            stopTimer();
            infoText.setText(R.string.pause);
        }else{
            startTimer();
            infoText.setText(R.string.perform_exercise);
        }
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                completedExercises++;
                exercisesCompleted.set(currentExerciseIndex, true);

                if(currentExerciseIndex < exercises.size()-1) {
                    currentExerciseIndex++;
                    changeExercise(currentExerciseIndex);
                }
            }
        }.start();

        stopStartButton.setImageResource(R.drawable.ic_pause);
        timeRunning = true;
    }

    public void stopTimer(){
        countDownTimer.cancel();
        stopStartButton.setImageResource(R.drawable.ic_play);
        timeRunning = false;
    }

    public void updateTimer(){
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText;
        timeLeftText = this.getResources().getString(R.string.time_remaining) + minutes;
        timeLeftText += ":";
        if(seconds < 10) timeLeftText+= "0";
        timeLeftText += seconds;

        timeRemaining.setText(timeLeftText);

        updateRemainingRepeats();
    }

    public void updateRemainingRepeats(){
        if (timeLeftInMilliseconds/1000 == timeToChangeRepeatCount) {
            repeatsRemaining.setText("x" + remainingRepeatsCount);
            remainingRepeatsCount--;
            timeToChangeRepeatCount -= timePerRepeat;

            //updating statistics
            if(timeLeftInMilliseconds != overallTime * 1000L) {
                courseTimeTaken += timePerRepeat;
                int repetitionsAttemptedNow = exercisesRepetitionsAttempted.get(currentExerciseIndex) + 1;
                exercisesRepetitionsAttempted.set(currentExerciseIndex, repetitionsAttemptedNow);
            }
        }
    }

    public void updateTitle(int exerciseIndex){
        if(Locale.getDefault().getLanguage().equals("lt") && !exercises.get(exerciseIndex).isCreatedByUser()){
            exerciseTitle.setText(exercises.get(exerciseIndex).getTitleInLithuanian());
        }else{
            exerciseTitle.setText(exercises.get(exerciseIndex).getTitleInEnglish());
        }
    }

    public void updateImage(int exerciseIndex){
        if(exercises.get(exerciseIndex).isCreatedByUser()){
            exerciseImage.setImageURI(Uri.parse(exercises.get(exerciseIndex).getUri()));
        }else{
            exerciseImage.setImageResource(exercises.get(exerciseIndex).getGif());
        }
    }

    public void updateTimeAndRepeatsRemaining(int exerciseIndex){
        CourseExerciseCrossRef courseExerciseCrossRef = null;
        try {
            courseExerciseCrossRef = courseExerciseCrossRefViewModel.getCrossRefByCourseIdAndExerciseId(courseId, exercises.get(exerciseIndex).getExerciseId());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        repeats = courseExerciseCrossRef.getRepeats();
        timePerRepeat = courseExerciseCrossRef.getTimePerRepeat();
        overallTime = repeats*timePerRepeat;
        timeToChangeRepeatCount = overallTime;
        remainingRepeatsCount = repeats;
        timeLeftInMilliseconds = overallTime * 1000L;
    }

    public void updateExerciseIndicator(int exerciseIndex){
        currentExercise.setText(exerciseIndex+1 +"/"+ exercises.size());
    }

    public void changeExercise(int exerciseIndex){
        if(timeRunning)
            stopTimer();

        updateTitle(exerciseIndex);
        updateExerciseIndicator(exerciseIndex);
        updateImage(exerciseIndex);
        updateTimeAndRepeatsRemaining(exerciseIndex);
        updateTimer();
    }

    public void prePopulateStatisticParameters(){
        courseTimeTaken = 0;
        courseAttemptedId = courseId;

        for(int i=0; i < exercises.size(); i++){
            exercisesAttemptedIds.add(exercises.get(i).getExerciseId());
            exercisesRepetitionsAttempted.add(0);
            exercisesCompleted.add(false);
        }
    }

    public void changeToEndWindow(){
        int []exercisesIds = new int[exercisesAttemptedIds.size()];
        int []exercisesRepsAttempted = new int[exercisesAttemptedIds.size()];
        boolean []completed = new boolean[exercisesAttemptedIds.size()];

        for(int i=0; i < exercisesAttemptedIds.size(); i++){
            exercisesIds[i] = exercisesAttemptedIds.get(i);
            exercisesRepsAttempted[i] = exercisesRepetitionsAttempted.get(i);
            completed[i] = exercisesCompleted.get(i);
        }

        int minutes = (int) courseTimeTaken / 60;
        int seconds = (int) courseTimeTaken % 60;

        String timeTakenString;
        timeTakenString = "" + minutes;
        timeTakenString += ":";
        if(seconds < 10) timeTakenString+= "0";
        timeTakenString += seconds;


        statistic = new Statistic(sqliteDate, courseTimeTaken, courseId, exercisesIds, exercisesRepsAttempted, completed);
        statisticViewModel.insert(statistic);

        Intent intent = new Intent(StartCourseActivity.this, CourseEndActivity.class);
        intent.putExtra(CourseEndActivity.EXTRA_EXERCISES_ATTEMPTED, "" + completedExercises + "/"+ exercises.size());
        intent.putExtra(CourseEndActivity.EXTRA_COURSE_TIME_TAKEN, timeTakenString);
        startActivity(intent);
    }


    public void createPopupWindowForExerciseDescription(String description){
        dialogBuilder = new AlertDialog.Builder(this);
        final View descriptionPopupView = getLayoutInflater().inflate(R.layout.popup_exercise_description, null);

        exerciseDescription = (TextView) descriptionPopupView.findViewById(R.id.popup_description);
        closeDialog = (ImageButton) descriptionPopupView.findViewById(R.id.close_description_popup);

        exerciseDescription.setText(description);
        exerciseDescription.setMovementMethod(new ScrollingMovementMethod());

        dialogBuilder.setView(descriptionPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void createPopupWindowForSettings(){

    }
}