package com.example.bakis;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ExerciseRepository {

    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allExercises;
    private LiveData<List<Exercise>> createdExercises;

    private LiveData<List<CourseWithExercises>> allExercisesOfCourse;

    public ExerciseRepository(Application application){
        Database database = Database.getInstance(application);
        exerciseDao = database.exerciseDao();
        allExercises = exerciseDao.getAllExercises();
        createdExercises = exerciseDao.getUserCreatedExercises();


        //allExercisesOfCourse = exerciseDao.getAllExercisesOfCourse(1);
    }

    public void insert(Exercise exercise){
        new InsertExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void update(Exercise exercise){
        new UpdateExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void delete(Exercise exercise){
        new DeleteExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    public LiveData<List<Exercise>> getUserCreatedExercises() {
        return createdExercises;
    }



    //public LiveData<List<CourseWithExercises>> getAllExercisesOfCourse(int courseId){return allExercisesOfCourse;}

    //public LiveData<List<ExerciseWithCourses>> getAllCoursesOfExercise(int exerciseId){return allCoursesOfExercise;}*/

    private static class InsertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao exerciseDao;

        private InsertExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.insert(exercises[0]);
            return null;
        }
    }

    private static class UpdateExerciseAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao exerciseDao;

        private UpdateExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.update(exercises[0]);
            return null;
        }
    }

    private static class DeleteExerciseAsyncTask extends AsyncTask<Exercise, Void, Void>{
        private ExerciseDao exerciseDao;

        private DeleteExerciseAsyncTask(ExerciseDao exerciseDao){
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.delete(exercises[0]);
            return null;
        }
    }

}
