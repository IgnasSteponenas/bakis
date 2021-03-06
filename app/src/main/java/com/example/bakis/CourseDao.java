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
public interface CourseDao {

    @Insert
    Long insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Transaction
    @Query("SELECT * FROM course_table WHERE rowid = :myRowId")
    Course getCourseByRowId(Long myRowId);

    @Transaction
    @Query("SELECT * FROM course_table WHERE courseId = :courseId")
    Course getCourseById(Integer courseId);

    @Query("SELECT * FROM course_table ORDER BY courseId DESC")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM course_table WHERE liked = 1 ORDER BY courseId DESC")
    LiveData<List<Course>> getAllLikedCourses();

    @Query("SELECT * FROM course_table WHERE createdByUser = 1 ORDER BY courseId DESC")
    LiveData<List<Course>> getUserCreatedCourses();
}
