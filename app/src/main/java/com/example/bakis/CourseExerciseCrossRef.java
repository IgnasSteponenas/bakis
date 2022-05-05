package com.example.bakis;

import androidx.room.Entity;

@Entity(primaryKeys = {"courseId", "exerciseId"})
public class CourseExerciseCrossRef {
    private int courseId;
    private int exerciseId;
    private int repeats;

    public CourseExerciseCrossRef(int courseId, int exerciseId, int repeats) {
        this.courseId = courseId;
        this.exerciseId = exerciseId;
        this.repeats = repeats;
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

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }
}
