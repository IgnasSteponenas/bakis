package com.example.bakis;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StatisticRepository {

    private StatisticDao statisticDao;
    private LiveData<List<Statistic>> allStatisticsLiveData;
    //private List<Statistic> allStatistics;

    public StatisticRepository(Application application){
        Database database = Database.getInstance(application);
        statisticDao = database.statisticDao();

        allStatisticsLiveData = statisticDao.getAllStatisticsLiveData();
        //allStatistics = statisticDao.getAllStatistics();
    }

    public void insert(Statistic statistic){
        new StatisticRepository.InsertStatisticAsyncTask(statisticDao).execute(statistic);
    }

    public LiveData<List<Statistic>> getAllStatisticsLiveData(){
        return allStatisticsLiveData;
    }

    public LiveData<List<Statistic>> getAllStatisticsLiveDataOnThisDay(Date date) throws ExecutionException, InterruptedException {
        return new GetAllStatisticsOnThisDayAsyncTask(statisticDao).execute(date).get();
    }

    public List<Statistic> getAllStatistics() throws ExecutionException, InterruptedException {
        return new GetAllStatisticsAsyncTask(statisticDao).execute().get();
    }

    private static class InsertStatisticAsyncTask extends AsyncTask<Statistic, Void, Void> {
        private StatisticDao statisticDao;

        private InsertStatisticAsyncTask(StatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(Statistic... statistics) {
            statisticDao.insert(statistics[0]);
            return null;
        }
    }

    private static class GetAllStatisticsAsyncTask extends AsyncTask<Void, Void, List<Statistic>>{
        private StatisticDao statisticDao;

        private GetAllStatisticsAsyncTask(StatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected List<Statistic> doInBackground(Void... voids) {
            return statisticDao.getAllStatistics();
        }
    }

    private static class GetAllStatisticsOnThisDayAsyncTask extends AsyncTask<Date, Void, LiveData<List<Statistic>>>{
        private StatisticDao statisticDao;

        private GetAllStatisticsOnThisDayAsyncTask(StatisticDao statisticDao){
            this.statisticDao = statisticDao;
        }

        @Override
        protected LiveData<List<Statistic>> doInBackground(Date... dates) {
            return statisticDao.getAllStatisticsLiveDataOnThisDay(dates[0]);
        }
    }
}
