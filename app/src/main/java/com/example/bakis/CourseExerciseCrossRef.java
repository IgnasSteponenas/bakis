package com.example.bakis;

import androidx.room.Entity;

@Entity(primaryKeys = {"courseId", "exerciseId"})
public class CourseExerciseCrossRef {
    private int courseId;
    private int exerciseId;

    public CourseExerciseCrossRef(int courseId, int exerciseId) {
        this.courseId = courseId;
        this.exerciseId = exerciseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
}
