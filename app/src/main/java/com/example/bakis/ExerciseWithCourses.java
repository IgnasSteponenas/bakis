package com.example.bakis;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;
import java.util.Locale;

public class ExerciseWithCourses {
    @Embedded public Exercise exercise;
    @Relation(
            parentColumn = "exerciseId",
            entityColumn = "courseId",
            associateBy = @Junction(CourseExerciseCrossRef.class)
    )
    public List<Course> courses;
}
