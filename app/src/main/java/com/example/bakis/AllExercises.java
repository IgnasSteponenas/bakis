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
import java.util.Locale;

public class AllExercises extends Fragment {

    private ExerciseViewModel exerciseViewModel;
    public static final int REVIEW_EXERCISE_REQUEST = 3;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_allexercises, container, false);

        getActivity().setTitle(R.string.all_exercises);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        context = getContext();

        ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                adapter.setExercises(exercises);
            }
        });

        adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {
                Intent intent = new Intent(context, ReviewExercise.class);

                if(Locale.getDefault().getLanguage().equals("lt") && exercise.getTitleInLithuanian() != null) {
                    intent.putExtra(ReviewExercise.EXTRA_TITLE, exercise.getTitleInLithuanian());
                    intent.putExtra(ReviewExercise.EXTRA_DESCRIPTION, exercise.getDescriptionInLithuanian());
                }else {
                    intent.putExtra(ReviewExercise.EXTRA_TITLE, exercise.getTitleInEnglish());
                    intent.putExtra(ReviewExercise.EXTRA_DESCRIPTION, exercise.getDescriptionInEnglish());
                }
                intent.putExtra(ReviewExercise.EXTRA_GIF, exercise.getGif());
                intent.putExtra(ReviewExercise.EXTRA_URI, exercise.getUri());
                startActivity(intent);
            }
        });

        return view;
    }
}
