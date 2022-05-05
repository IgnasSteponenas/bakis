package com.example.bakis;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CourseExerciseCrossRefDao {

    @Insert
    void insertCourseExerciseCrossRef(CourseExerciseCrossRef crossRef);

    @Delete
    void deleteCourseExerciseCrossRef(CourseExerciseCrossRef crossRef);

    @Query("SELECT * FROM CourseExerciseCrossRef ORDER BY courseId DESC")
    LiveData<List<CourseExerciseCrossRef>> getAllCourseExerciseCrossRef();

    @Transaction
    @Query("SELECT * FROM course_table WHERE courseId = :courseId ")
    LiveData<List<CourseWithExercises>> getAllExercisesOfCourse(int courseId);

    @Transaction
    @Query("SELECT * FROM exercise_table WHERE exerciseId = :exerciseId ")
    LiveData<List<ExerciseWithCourses>> getAllCoursesOfExercise(int exerciseId);
}
