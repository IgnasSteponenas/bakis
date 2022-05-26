package com.example.bakis;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StatisticViewModel extends AndroidViewModel {
    private StatisticRepository repository;
    private LiveData<List<Statistic>> allStatisticsLiveData;
    //private List<Statistic> allStatistics;

    public StatisticViewModel(@NonNull Application application) {
        super(application);
        repository = new StatisticRepository(application);
        allStatisticsLiveData = repository.getAllStatisticsLiveData();
        //allStatistics = repository.getAllStatistics();
    }

    public void insert(Statistic statistic){
        repository.insert(statistic);
    }

    public LiveData<List<Statistic>> getAllStatisticsLiveData() {
        return allStatisticsLiveData;
    }

    public LiveData<List<Statistic>> getAllStatisticsLiveDataOnThisDay(Date date) throws ExecutionException, InterruptedException {
        return repository.getAllStatisticsLiveDataOnThisDay(date);
    }

    public List<Statistic> getAllStatistics() throws ExecutionException, InterruptedException {
        return repository.getAllStatistics();
    }
}
