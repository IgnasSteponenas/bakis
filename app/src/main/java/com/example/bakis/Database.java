package com.example.bakis;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import kotlin.jvm.Volatile;

@androidx.room.Database(entities = {Exercise.class, Course.class, CourseExerciseCrossRef.class}, version = 2)
public abstract class Database extends RoomDatabase {

    @Volatile
    private static Database instance;

    public abstract ExerciseDao exerciseDao();
    public abstract CourseDao courseDao();
    public abstract CourseExerciseCrossRefDao courseExerciseCrossRefDao();

    public static synchronized Database getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "database")
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
        private CourseDao courseDao;
        private CourseExerciseCrossRefDao courseExerciseCrossRefDao;

        private PopulateDbAsyncTask(Database db){
            exerciseDao = db.exerciseDao();
            courseDao = db.courseDao();
            courseExerciseCrossRefDao = db.courseExerciseCrossRefDao();
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

            courseDao.insertCourse(new Course("course1", false, true));
            courseDao.insertCourse(new Course("course2", false, false));
            courseDao.insertCourse(new Course("course3", false, false));
            courseDao.insertCourse(new Course("course4", false, true));

            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(1, 1, 20, 10));
            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(1, 2, 15, 10));
            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(1, 3, 32, 10));
            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(new CourseExerciseCrossRef(1, 4, 10, 10));

            //LiveData<List<CourseWithExercises>> courseWithExercises = exerciseDao.getAllExercisesOfCourse(1);

            //courseWithExercises.getValue().get(1).

            /*courseWithExercises.

            System.out.println();*/

            return null;
        }
    }
}
