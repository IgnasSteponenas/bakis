package com.example.bakis;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("SELECT * FROM exercise_table ORDER BY exerciseId DESC")
    LiveData<List<Exercise>> getAllExercises();

    @Query("SELECT * FROM exercise_table WHERE createdByUser = 1 ORDER BY exerciseId DESC")
    LiveData<List<Exercise>> getUserCreatedExercises();
//////////////////////////////////////////
    @Insert
    void insertCourse(Course course);

    @Insert
    void insertCourseExerciseCrossRef(CourseExerciseCrossRef crossRef);

    @Query("SELECT * FROM course_table ORDER BY courseId DESC")
    LiveData<List<Course>> getAllCourses();

    @Transaction
    @Query("SELECT * FROM course_table WHERE courseId = :courseId ")
    LiveData<List<CourseWithExercises>> getAllExercisesOfCourse(int courseId);

    @Transaction
    @Query("SELECT * FROM exercise_table WHERE exerciseId = :exerciseId ")
    LiveData<List<ExerciseWithCourses>> getAllCoursesOfExercise(int exerciseId);
}
