package com.example.bakis;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CreatedExercises extends Fragment {

    public static final int ADD_EXERCISE_REQUEST = 1;
    public static final int EDIT_EXERCISE_REQUEST = 2;

    private ExerciseViewModel exerciseViewModel;
    private CourseExerciseCrossRefViewModel courseExerciseCrossRefViewModel;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_created_exercises, container, false);
        context = getContext();

        getActivity().setTitle(R.string.my_created_exercises);

        FloatingActionButton buttonAddExercise = view.findViewById(R.id.button_add_exercise);
        buttonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditExercise.class);
                startActivityForResult(intent, ADD_EXERCISE_REQUEST);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        courseExerciseCrossRefViewModel = new ViewModelProvider(this).get(CourseExerciseCrossRefViewModel.class);

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exerciseViewModel.getUserCreatedExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> userCreatedExercises) {
                adapter.setExercises(userCreatedExercises);
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
                Exercise exercise = adapter.getExerciseAt(viewHolder.getAdapterPosition());

                courseExerciseCrossRefViewModel.deleteByExerciseId(exercise.getExerciseId());

                exerciseViewModel.delete(adapter.getExerciseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(context, R.string.exercise_deleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(context, AddEditExercise.class);
                intent.putExtra(AddEditExercise.EXTRA_ID, exercise.getExerciseId());
                intent.putExtra(AddEditExercise.EXTRA_TITLE, exercise.getTitleInEnglish());
                intent.putExtra(AddEditExercise.EXTRA_DESCRIPTION, exercise.getDescriptionInEnglish());
                intent.putExtra(AddEditExercise.EXTRA_GIF, exercise.getUri());
                startActivityForResult(intent, EDIT_EXERCISE_REQUEST);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EXERCISE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditExercise.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditExercise.EXTRA_DESCRIPTION);
            String uri = data.getStringExtra(AddEditExercise.EXTRA_GIF);

            Exercise exercise = new Exercise(title, description, null, null, 0, uri, true);
            exerciseViewModel.insert(exercise);

            Toast.makeText(getContext(), R.string.exercise_saved, Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_EXERCISE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditExercise.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(context, R.string.exercise_not_updated, Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditExercise.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditExercise.EXTRA_DESCRIPTION);
            String uri = data.getStringExtra(AddEditExercise.EXTRA_GIF);

            Exercise exercise = new Exercise(title, description, null, null, 0, uri, true);
            exercise.setExerciseId(id);
            exerciseViewModel.update(exercise);

            Toast.makeText(context, R.string.exercise_updated, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), R.string.exercise_not_saved, Toast.LENGTH_SHORT).show();
        }
    }
}