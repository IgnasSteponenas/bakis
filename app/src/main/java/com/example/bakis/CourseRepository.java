package com.example.bakis;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CourseRepository {

    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Course>> allLikedCourses;
    private LiveData<List<Course>> createdCourses;

    public CourseRepository(Application application){
        Database database = Database.getInstance(application);
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();
        allLikedCourses = courseDao.getAllLikedCourses();
        createdCourses = courseDao.getUserCreatedCourses();

    }

    public void insert(Course course){
        new CourseRepository.InsertCourseAsyncTask(courseDao).execute(course);
    }

    public void update(Course course){
        new CourseRepository.UpdateCourseAsyncTask(courseDao).execute(course);
    }

    public void delete(Course course){
        new CourseRepository.DeleteCourseAsyncTask(courseDao).execute(course);
    }

    public LiveData<List<Course>> getAllCourses(){return allCourses;}

    public LiveData<List<Course>> getAllLikedCourses(){return allLikedCourses;}

    public LiveData<List<Course>> getUserCreatedCourses() {
        return createdCourses;
    }


    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insertCourse(courses[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void>{
        private CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.updateCourse(courses[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void>{
        private CourseDao courseDao;

        private DeleteCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.deleteCourse(courses[0]);
            return null;
        }
    }
}
