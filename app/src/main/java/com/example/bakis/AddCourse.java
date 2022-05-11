package com.example.bakis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddCourse extends AppCompatActivity {
    public static final String EXTRA_COURSE_ID =
            "com.example.bakis.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_TITLE =
            "com.example.bakis.EXTRA_COURSE_TITLE";
    public static final String EXTRA_CREATED_BY_USER =
            "com.example.bakis.EXTRA_CREATED_BY_USER";
    public static final String EXTRA_LIKED =
            "com.example.bakis.EXTRA_LIKED";

    ExerciseViewModel exerciseViewModel;
    CourseViewModel courseViewModel;
    CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;
    EditText courseTitle;
    AddCourseAdapter adapter;

    private List<Exercise> selected = new ArrayList<>();
    private List<Integer> selectedRepeats = new ArrayList<>();
    private List<Integer> selectedTimePerRepeat = new ArrayList<>();
    private Boolean editCourse = false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        courseTitle = findViewById(R.id.course_add_text_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        RecyclerView recyclerView = findViewById(R.id.recycler_view_add_course_all_exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseExerciseCrossRefViewModel = new ViewModelProvider(this).get(CourseExerciseCrossRefViewModel.class);

        intent = getIntent();

        adapter = new AddCourseAdapter(this);
        recyclerView.setAdapter(adapter);

        if(intent.hasExtra(EXTRA_COURSE_ID)){
            setTitle(R.string.edit_course);
            editCourse = true;

            courseTitle.setText(intent.getStringExtra(EXTRA_COURSE_TITLE));

            if(!intent.getBooleanExtra(EXTRA_CREATED_BY_USER, false))
                disableInput(courseTitle);

            Integer courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);

            List<Exercise> courseSelectedExercises = new ArrayList<>();

            try {
                courseSelectedExercises = courseExerciseCrossRefViewModel.getAllExercisesOfCourse(courseId).exercises;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i = 0; i<courseSelectedExercises.size(); i++){
                CourseExerciseCrossRef courseExerciseCrossRef = null;
                try {
                    courseExerciseCrossRef = courseExerciseCrossRefViewModel.getCrossRefByCourseIdAndExerciseId(courseId, courseSelectedExercises.get(i).getExerciseId());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                selectedRepeats.add(courseExerciseCrossRef.getRepeats());
                selectedTimePerRepeat.add(courseExerciseCrossRef.getTimePerRepeat());
            }

            adapter.setSelected(courseSelectedExercises);
            adapter.setSelectedRepeats(selectedRepeats);
            adapter.setSelectedTimePerRepeat(selectedTimePerRepeat);
        }else{
            setTitle(R.string.add_course);
            editCourse = false;
            courseTitle.setEnabled(true);
        }







        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercises().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                adapter.setExercises(exercises);
            }
        });
    }

    private void disableInput(EditText editText){
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
        editText.setBackground(null);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return true;  // Blocks input from hardware keyboards.
            }
        });
    }

    public void add() throws ExecutionException, InterruptedException {
        List<Exercise> selected = new ArrayList<>();
        List<Integer> selectedRepeats = new ArrayList<>();
        List<Integer> selectedTimePerRepeat = new ArrayList<>();

        String title = courseTitle.getText().toString();

        selected = adapter.getSelected();
        selectedRepeats = adapter.getSelectedRepeats();
        selectedTimePerRepeat = adapter.getSelectedTimePerRepeat();

        if(!title.isEmpty() && !selected.isEmpty() && !selectedRepeats.isEmpty() && !selectedTimePerRepeat.isEmpty() && !editCourse) {
            Course course = new Course(title, null, true, false);
            Long rowid = courseViewModel.insert(course);
            course = null;
            if(rowid != null) {
                course = courseViewModel.getCourseByRowI(rowid);
                int courseId = -1;
                courseId = course.getCourseId();

                if(courseId != -1) {
                    for (int i = 0; i < selected.size(); i++) {
                        courseExerciseCrossRefViewModel.insert(new CourseExerciseCrossRef(courseId,
                                selected.get(i).getExerciseId(),
                                selectedRepeats.get(i),
                                selectedTimePerRepeat.get(i)));
                    }

                    Toast.makeText(this, R.string.course_added, Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(this, R.string.course_not_saved, Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, R.string.course_not_saved, Toast.LENGTH_SHORT).show();
        }else if(!title.isEmpty() && !selected.isEmpty() && !selectedRepeats.isEmpty() && !selectedTimePerRepeat.isEmpty() && editCourse){
            //EXTRA_COURSE_ID intent.getStringExtra(EXTRA_COURSE_TITLE)
            Course course = new Course(title, null, true, intent.getBooleanExtra(EXTRA_LIKED, false));
            int courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);

            if(courseId!=-1) {
                course.setCourseId(courseId);
                courseViewModel.update(course);

                courseExerciseCrossRefViewModel.deleteByCourseId(courseId);

                for (int i = 0; i < selected.size(); i++) {
                    courseExerciseCrossRefViewModel.insert(new CourseExerciseCrossRef(courseId,
                            selected.get(i).getExerciseId(),
                            selectedRepeats.get(i),
                            selectedTimePerRepeat.get(i)));
                }
            }
        }else{
            Toast.makeText(this, R.string.course_not_saved, Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_course_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                try {
                    add();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}