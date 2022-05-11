package com.example.bakis;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CourseWithExercises {
    @Embedded public Course course;
    @Relation(
            parentColumn = "courseId",
            entityColumn = "exerciseId",
            associateBy = @Junction(CourseExerciseCrossRef.class)
    )
    public List<Exercise> exercises;
    /*public int exerciseCount;

    {
        assert exercises != null;
        exerciseCount = exercises.size();
    }*/
}
