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
            exerciseDao.insert(new Exercise("title 1", "desc 1"));
            exerciseDao.insert(new Exercise("title 2", "desc 2"));
            exerciseDao.insert(new Exercise("title 3", "desc 3"));

            return null;
        }
    }
}
