package com.example.bakis;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Exercise.class}, version = 1)
public abstract class ExerciseDatabase extends RoomDatabase {

    private static ExerciseDatabase instance;

    public abstract ExerciseDao exerciseDao();

    public static synchronized  ExerciseDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExerciseDatabase.class, "exercise_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private ExerciseDao exerciseDao;

        private PopulateDbAsyncTask(ExerciseDatabase db){
            exerciseDao = db.exerciseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            exerciseDao.insert(new Exercise("title 4", "desc 4", R.drawable.thanos4, null, false));
            exerciseDao.insert(new Exercise("title 3", "desc 3", R.drawable.thanos3, null, false));
            exerciseDao.insert(new Exercise("title 2", "desc 2", R.drawable.thanos2, null, false));
            exerciseDao.insert(new Exercise("title 1", "desc 1", R.drawable.thanos1, null, false));
            exerciseDao.insert(new Exercise("title 8", "desc 8", R.drawable.thanos4, null, false));
            exerciseDao.insert(new Exercise("title 7", "desc 7", R.drawable.thanos3, null, false));
            exerciseDao.insert(new Exercise("title 6", "desc 6", R.drawable.thanos2, null, false));
            exerciseDao.insert(new Exercise("title 5", "desc 5", R.drawable.thanos1, null, false));
            exerciseDao.insert(new Exercise("title 12", "desc 12", R.drawable.thanos4, null, false));
            exerciseDao.insert(new Exercise("title 11", "desc 11", R.drawable.thanos3, null, false));
            exerciseDao.insert(new Exercise("title 10", "desc 10", R.drawable.thanos2, null, false));
            exerciseDao.insert(new Exercise("title 9", "desc 9", R.drawable.thanos1, null, false));

            return null;
        }
    }
}
