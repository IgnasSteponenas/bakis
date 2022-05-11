package com.example.bakis;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int exerciseId;
    private String titleInEnglish;
    private String descriptionInEnglish;
    private String titleInLithuanian;
    private String descriptionInLithuanian;
    private int gif;
    private String uri;
    private boolean createdByUser;

    public Exercise(String titleInEnglish, String descriptionInEnglish, String titleInLithuanian, String descriptionInLithuanian, int gif, String uri, boolean createdByUser) {
        this.titleInEnglish = titleInEnglish;
        this.descriptionInEnglish = descriptionInEnglish;
        this.titleInLithuanian = titleInLithuanian;
        this.descriptionInLithuanian = descriptionInLithuanian;
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

    public String getTitleInEnglish() {
        return titleInEnglish;
    }

    public void setTitleInEnglish(String titleInEnglish) {
        this.titleInEnglish = titleInEnglish;
    }

    public String getDescriptionInEnglish() {
        return descriptionInEnglish;
    }

    public void setDescriptionInEnglish(String descriptionInEnglish) {
        this.descriptionInEnglish = descriptionInEnglish;
    }

    public String getTitleInLithuanian() {
        return titleInLithuanian;
    }

    public void setTitleInLithuanian(String titleInLithuanian) {
        this.titleInLithuanian = titleInLithuanian;
    }

    public String getDescriptionInLithuanian() {
        return descriptionInLithuanian;
    }

    public void setDescriptionInLithuanian(String descriptionInLithuanian) {
        this.descriptionInLithuanian = descriptionInLithuanian;
    }

    public void setGif(int gif) {
        this.gif = gif;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setCreatedByUser(boolean createdByUser) {
        this.createdByUser = createdByUser;
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
