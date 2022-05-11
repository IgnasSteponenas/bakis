package com.example.bakis;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "statistics_table")
public class Statistic {

    @PrimaryKey(autoGenerate = true)
    private int statisticId;
    private Date dateOfExercise;
    private int courseTimeTaken;
    private int courseAttemptedId;
    private int[] exercisesAttemptedIds;
    private int[] exercisesRepetitionsAttempted;

    public Statistic(Date dateOfExercise, int courseTimeTaken, int courseAttemptedId, int[] exercisesAttemptedIds, int[] exercisesRepetitionsAttempted) {
        this.dateOfExercise = dateOfExercise;
        this.courseTimeTaken = courseTimeTaken;
        this.courseAttemptedId = courseAttemptedId;
        this.exercisesAttemptedIds = exercisesAttemptedIds;
        this.exercisesRepetitionsAttempted = exercisesRepetitionsAttempted;
    }

    public void setStatisticId(int statisticId) {
        this.statisticId = statisticId;
    }

    public int getStatisticId() {
        return statisticId;
    }

    public Date getDateOfExercise() {
        return dateOfExercise;
    }

    public void setDateOfExercise(Date dateOfExercise) {
        this.dateOfExercise = dateOfExercise;
    }

    public int getCourseTimeTaken() {
        return courseTimeTaken;
    }

    public void setCourseTimeTaken(int courseTimeTaken) {
        this.courseTimeTaken = courseTimeTaken;
    }

    public int getCourseAttemptedId() {
        return courseAttemptedId;
    }

    public void setCourseAttemptedId(int courseAttemptedId) {
        this.courseAttemptedId = courseAttemptedId;
    }

    public int[] getExercisesAttemptedIds() {
        return exercisesAttemptedIds;
    }

    public void setExercisesAttemptedIds(int[] exercisesAttemptedIds) {
        this.exercisesAttemptedIds = exercisesAttemptedIds;
    }

    public int[] getExercisesRepetitionsAttempted() {
        return exercisesRepetitionsAttempted;
    }

    public void setExercisesRepetitionsAttempted(int[] exercisesRepetitionsAttempted) {
        this.exercisesRepetitionsAttempted = exercisesRepetitionsAttempted;
    }
}
