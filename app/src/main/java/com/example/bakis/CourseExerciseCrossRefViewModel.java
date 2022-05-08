package com.example.bakis;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CourseExerciseCrossRefViewModel extends AndroidViewModel {

    private CourseExerciseCrossRefRepository repository;
    private List<CourseWithExercises> allExercisesOfCourse;
    private LiveData<List<ExerciseWithCourses>> allCoursesOfExercise;
    private LiveData<List<CourseExerciseCrossRef>> allCourseExerciseCrossRef;
    private List<LiveData<List<CourseWithExercises>>> allTest;


    public CourseExerciseCrossRefViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseExerciseCrossRefRepository(application);
        allCourseExerciseCrossRef = repository.getAllCourseExerciseCrossRef();
    }


    public void insert(CourseExerciseCrossRef courseExerciseCrossRef){
        repository.insert(courseExerciseCrossRef);
    }

    public void delete(CourseExerciseCrossRef courseExerciseCrossRef){
        repository.delete(courseExerciseCrossRef);
    }

    public void deleteByCourseId(Integer courseId){
        repository.deleteCrossRefByCourseId(courseId);
    }

    public void deleteByExerciseId(Integer exerciseId){
        repository.deleteCrossRefByExerciseId(exerciseId);
    }

    public CourseExerciseCrossRef getCrossRefByCourseIdAndExerciseId(Integer courseId, Integer exerciseId) throws ExecutionException, InterruptedException {
        return repository.getCourseExerciseCrossRefByCourseIdAndExerciseId(courseId, exerciseId);
    }

    public CourseWithExercises getAllExercisesOfCourse(int courseId) throws ExecutionException, InterruptedException {return repository.getAllExercisesOfCourse(courseId);}

    public LiveData<List<ExerciseWithCourses>> getAllCoursesOfExercise(int exerciseId){return repository.getAllCoursesOfExercise(exerciseId);}

    public LiveData<List<CourseExerciseCrossRef>> getAllCourseExerciseCrossRef() {
        return allCourseExerciseCrossRef;
    }


}
