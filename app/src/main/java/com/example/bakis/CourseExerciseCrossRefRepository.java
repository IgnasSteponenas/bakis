package com.example.bakis;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CourseExerciseCrossRefRepository {
    private CourseExerciseCrossRefDao courseExerciseCrossRefDao;
    private CourseExerciseCrossRef courseExerciseCrossRef;
    //private List<CourseWithExercises> allExercisesOfCourse;
    private LiveData<List<ExerciseWithCourses>> allCoursesOfExercise;
    private LiveData<List<CourseExerciseCrossRef>> allCourseExerciseCrossRef;

    public CourseExerciseCrossRefRepository(Application application){
        Database database = Database.getInstance(application);
        courseExerciseCrossRefDao = database.courseExerciseCrossRefDao();
        allCourseExerciseCrossRef = courseExerciseCrossRefDao.getAllCourseExerciseCrossRef();

        //allExercisesOfCourse = courseExerciseCrossRefDao;
        allCoursesOfExercise = courseExerciseCrossRefDao.getAllCoursesOfExercise(0);

        /*allCourses = courseDao.getAllCourses();
        createdCourses = courseDao.getUserCreatedCourses();*/

    }

    public void insert(CourseExerciseCrossRef courseExerciseCrossRef){
        new CourseExerciseCrossRefRepository.InsertCourseExerciseCrossRefAsyncTask(courseExerciseCrossRefDao).execute(courseExerciseCrossRef);
    }

    public void delete(CourseExerciseCrossRef courseExerciseCrossRef){
        new CourseExerciseCrossRefRepository.DeleteCourseExerciseCrossRefAsyncTask(courseExerciseCrossRefDao).execute(courseExerciseCrossRef);
    }

    public void deleteCrossRefByCourseId(Integer courseId){
        new CourseExerciseCrossRefRepository.DeleteCrossRefByCourseIdAsyncTask(courseExerciseCrossRefDao).execute(courseId);
    }

    public void deleteCrossRefByExerciseId(Integer exerciseId){
        new CourseExerciseCrossRefRepository.DeleteCrossRefByExerciseIdAsyncTask(courseExerciseCrossRefDao).execute(exerciseId);
    }

    public CourseExerciseCrossRef getCourseExerciseCrossRefByCourseIdAndExerciseId(Integer courseId, Integer exerciseId) throws ExecutionException, InterruptedException {
        return new CourseExerciseCrossRefRepository.GetCourseExerciseCrossRefByCourseIdAndExerciseId(courseExerciseCrossRefDao).execute(courseId, exerciseId).get();
    }

    public CourseWithExercises getAllExercisesOfCourse(int courseId) throws ExecutionException, InterruptedException {
        return new CourseExerciseCrossRefRepository.GetAllExercisesOfCourseAsyncTask(courseExerciseCrossRefDao).execute(courseId).get();
    }

    public LiveData<List<ExerciseWithCourses>> getAllCoursesOfExercise(int exerciseId){return allCoursesOfExercise;}

    public LiveData<List<CourseExerciseCrossRef>> getAllCourseExerciseCrossRef(){return allCourseExerciseCrossRef;}

    private static class InsertCourseExerciseCrossRefAsyncTask extends AsyncTask<CourseExerciseCrossRef, Void, Void> {
        private CourseExerciseCrossRefDao courseExerciseCrossRefDao;

        private InsertCourseExerciseCrossRefAsyncTask(CourseExerciseCrossRefDao courseExerciseCrossRefDao){
            this.courseExerciseCrossRefDao = courseExerciseCrossRefDao;
        }

        @Override
        protected Void doInBackground(CourseExerciseCrossRef... courseExerciseCrossRefs) {
            courseExerciseCrossRefDao.insertCourseExerciseCrossRef(courseExerciseCrossRefs[0]);
            return null;
        }
    }

    private static class DeleteCourseExerciseCrossRefAsyncTask extends AsyncTask<CourseExerciseCrossRef, Void, Void>{
        private CourseExerciseCrossRefDao courseExerciseCrossRefDao;

        private DeleteCourseExerciseCrossRefAsyncTask(CourseExerciseCrossRefDao courseExerciseCrossRefDao){
            this.courseExerciseCrossRefDao = courseExerciseCrossRefDao;
        }

        @Override
        protected Void doInBackground(CourseExerciseCrossRef... courseExerciseCrossRefs) {
            courseExerciseCrossRefDao.deleteCourseExerciseCrossRef(courseExerciseCrossRefs[0]);
            return null;
        }
    }

    private static class DeleteCrossRefByCourseIdAsyncTask extends AsyncTask<Integer, Void, Void>{
        private CourseExerciseCrossRefDao courseExerciseCrossRefDao;

        private DeleteCrossRefByCourseIdAsyncTask(CourseExerciseCrossRefDao courseExerciseCrossRefDao){
            this.courseExerciseCrossRefDao = courseExerciseCrossRefDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            courseExerciseCrossRefDao.deleteCrossRefByCourseId(integers[0]);
            return null;
        }

    }

    private static class DeleteCrossRefByExerciseIdAsyncTask extends AsyncTask<Integer, Void, Void>{
        private CourseExerciseCrossRefDao courseExerciseCrossRefDao;

        private DeleteCrossRefByExerciseIdAsyncTask(CourseExerciseCrossRefDao courseExerciseCrossRefDao){
            this.courseExerciseCrossRefDao = courseExerciseCrossRefDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            courseExerciseCrossRefDao.deleteCrossRefByExerciseId(integers[0]);
            return null;
        }

    }

    private static class GetAllExercisesOfCourseAsyncTask extends AsyncTask<Integer, Void, CourseWithExercises>{
        private CourseExerciseCrossRefDao courseExerciseCrossRefDao;

        private GetAllExercisesOfCourseAsyncTask(CourseExerciseCrossRefDao courseExerciseCrossRefDao){
            this.courseExerciseCrossRefDao = courseExerciseCrossRefDao;
        }

        @Override
        protected CourseWithExercises doInBackground(Integer... integers) {
            return courseExerciseCrossRefDao.getAllExercisesOfCourse(integers[0]);
        }
    }

    private static class GetCourseExerciseCrossRefByCourseIdAndExerciseId extends AsyncTask<Integer, Void, CourseExerciseCrossRef>{
        private CourseExerciseCrossRefDao courseExerciseCrossRefDao;

        private GetCourseExerciseCrossRefByCourseIdAndExerciseId(CourseExerciseCrossRefDao courseExerciseCrossRefDao){
            this.courseExerciseCrossRefDao = courseExerciseCrossRefDao;
        }

        @Override
        protected CourseExerciseCrossRef doInBackground(Integer... integers) {
            return courseExerciseCrossRefDao.getCourseExerciseCrossRefByCourseIdAndExerciseId(integers[0], integers[1]);
        }
    }
}
