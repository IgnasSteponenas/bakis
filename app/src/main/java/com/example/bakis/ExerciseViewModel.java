package com.example.bakis;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository repository;
    private LiveData<List<Exercise>> allExercises;
    private LiveData<List<Exercise>> userCreatedExercises;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
        allExercises = repository.getAllExercises();
        userCreatedExercises = repository.getUserCreatedExercises();
    }

    public void insert(Exercise exercise){
        repository.insert(exercise);
    }

    public void update(Exercise exercise){
        repository.update(exercise);
    }

    public void delete(Exercise exercise){
        repository.delete(exercise);
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public LiveData<List<Exercise>> getUserCreatedExercises() {
        return userCreatedExercises;
    }
}
