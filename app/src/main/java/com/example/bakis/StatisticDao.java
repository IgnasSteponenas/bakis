package com.example.bakis;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface StatisticDao {

    @Insert
    void insert(Statistic statistic);

    @Update
    void update(Statistic statistic);

    @Delete
    void delete(Statistic statistic);

    @Query("SELECT * FROM statistics_table ORDER BY statisticId DESC")
    LiveData<List<Statistic>> getAllStatisticsLiveData();

    @Transaction
    @Query("SELECT * FROM statistics_table WHERE dateOfExercise = :dateOfExercise ORDER BY statisticId ASC")
    LiveData<List<Statistic>> getAllStatisticsLiveDataOnThisDay(Date dateOfExercise);

    @Query("SELECT * FROM statistics_table ORDER BY statisticId DESC")
    List<Statistic> getAllStatistics();

    /*@Query("SELECT * FROM exercise_table WHERE createdByUser = 1 ORDER BY exerciseId DESC")
    LiveData<List<Exercise>> getUserCreatedExercises();*/

}
