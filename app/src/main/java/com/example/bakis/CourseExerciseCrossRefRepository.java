package com.example.bakis;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseExerciseCrossRefRepository {
    private CourseExerciseCrossRefDao courseExerciseCrossRefDao;
    private LiveData<List<CourseWithExercises>> allExercisesOfCourse;
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

    public LiveData<List<CourseWithExercises>> getAllExercisesOfCourse(int courseId){
        allExercisesOfCourse = courseExerciseCrossRefDao.getAllExercisesOfCourse(courseId);
        return allExercisesOfCourse;
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
}
