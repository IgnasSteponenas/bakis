package com.example.bakis;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String titleInEnglish;
    private String titleInLithuanian;
    private boolean createdByUser;
    private boolean liked;

    public Course(String titleInEnglish, String titleInLithuanian, boolean createdByUser, boolean liked) {
        this.titleInEnglish = titleInEnglish;
        this.titleInLithuanian = titleInLithuanian;
        this.createdByUser = createdByUser;
        this.liked = liked;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public boolean isCreatedByUser() {
        return createdByUser;
    }

    public String getTitleInEnglish() {
        return titleInEnglish;
    }

    public void setTitleInEnglish(String titleInEnglish) {
        this.titleInEnglish = titleInEnglish;
    }

    public String getTitleInLithuanian() {
        return titleInLithuanian;
    }

    public void setTitleInLithuanian(String titleInLithuanian) {
        this.titleInLithuanian = titleInLithuanian;
    }

    public void setCreatedByUser(boolean createdByUser) {
        this.createdByUser = createdByUser;
    }
}
