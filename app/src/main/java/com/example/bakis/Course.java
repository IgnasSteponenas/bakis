package com.example.bakis;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String title;
    private boolean createdByUser;
    //private List<Exercise> courseExercises;
    //private int[] repeats;

    public Course(String title, boolean createdByUser) {
        this.title = title;
        this.createdByUser = createdByUser;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCreatedByUser() {
        return createdByUser;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedByUser(boolean createdByUser) {
        this.createdByUser = createdByUser;
    }
}
