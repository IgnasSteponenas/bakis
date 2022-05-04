package com.example.bakis;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int exerciseId;
    private String title;
    private String description;
    private int gif;
    private String uri;
    private boolean createdByUser;

    public Exercise(String title, String description, int gif, String uri, boolean createdByUser) {
        this.title = title;
        this.description = description;
        this.gif = gif;
        this.uri = uri;
        this.createdByUser = createdByUser;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getGif() {
        return gif;
    }

    public boolean isCreatedByUser() {
        return createdByUser;
    }

    public String getUri() {
        return uri;
    }
}
