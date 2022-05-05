package com.example.bakis;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private CourseRepository repository;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Course>> allLikedCourses;
    private LiveData<List<Course>> userCreatedCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
        allCourses = repository.getAllCourses();
        allLikedCourses = repository.getAllLikedCourses();
        userCreatedCourses = repository.getUserCreatedCourses();

    }

    public void insert(Course course){
        repository.insert(course);
    }

    public void update(Course course){
        repository.update(course);
    }

    public void delete(Course course){
        repository.delete(course);
    }

    public LiveData<List<Course>> getAllCourses(){return allCourses;}

    public LiveData<List<Course>> getAllLikedCourses(){return allLikedCourses;}

    public LiveData<List<Course>> getUserCreatedCourses() {
        return userCreatedCourses;
    }

}
