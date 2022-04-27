package com.example.bakis;

import static android.app.Activity.RESULT_OK;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AllExercises extends Fragment {

    public static final int ADD_EXERCISE_REQUEST = 1;
    public static final int EDIT_EXERCISE_REQUEST = 2;

    private ExerciseViewModel exerciseViewModel;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_allexercises, container, false);
        context = getContext();

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

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                adapter.setExercises(exercises);
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
                exerciseViewModel.delete(adapter.getExerciseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(context, "Exercise deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(context, AddEditExercise.class);
                intent.putExtra(AddEditExercise.EXTRA_ID, exercise.getId());
                intent.putExtra(AddEditExercise.EXTRA_TITLE, exercise.getTitle());
                intent.putExtra(AddEditExercise.EXTRA_DESCRIPTION, exercise.getDescription());
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

            Exercise exercise = new Exercise(title, description);
            exerciseViewModel.insert(exercise);

            Toast.makeText(getContext(), "Exercise saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_EXERCISE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditExercise.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(context, "Exercise can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditExercise.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditExercise.EXTRA_DESCRIPTION);

            Exercise exercise = new Exercise(title, description);
            exercise.setId(id);
            exerciseViewModel.update(exercise);

            Toast.makeText(context, "Exercise updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Exercise not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
