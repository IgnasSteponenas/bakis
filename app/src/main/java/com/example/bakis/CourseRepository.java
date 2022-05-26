package com.example.bakis;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CourseRepository {

    private CourseDao courseDao;
    Long lastId;
    private Course course;
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

    public Long insert(Course course) throws ExecutionException, InterruptedException {
       return new CourseRepository.InsertCourseAsyncTask(courseDao).execute(course).get();
    }

    public void update(Course course){
        new CourseRepository.UpdateCourseAsyncTask(courseDao).execute(course);
    }

    public void delete(Course course){
        new CourseRepository.DeleteCourseAsyncTask(courseDao).execute(course);
    }

    public Course getCourseByRowId (Long rowid) throws ExecutionException, InterruptedException {
        return new CourseRepository.GetCourseByRowId(courseDao).execute(rowid).get();
    }

    public Course getCourseById (Integer id) throws ExecutionException, InterruptedException {
        return new CourseRepository.GetCourseById(courseDao).execute(id).get();
    }

    public LiveData<List<Course>> getAllCourses(){return allCourses;}

    public LiveData<List<Course>> getAllLikedCourses(){return allLikedCourses;}

    public LiveData<List<Course>> getUserCreatedCourses() {
        return createdCourses;
    }

    private static class GetCourseByRowId extends AsyncTask<Long, Void, Course>{
        private CourseDao courseDao;

        private GetCourseByRowId(CourseDao courseDao) {this.courseDao = courseDao;}

        @Override
        protected Course doInBackground(Long... longs) {
            Course course;

            course = courseDao.getCourseByRowId(longs[0]);

            return course;
        }
    }

    private static class GetCourseById extends AsyncTask<Integer, Void, Course>{
        private CourseDao courseDao;

        private GetCourseById(CourseDao courseDao) {this.courseDao = courseDao;}

        @Override
        protected Course doInBackground(Integer... integers) {
            Course course;

            course = courseDao.getCourseById(integers[0]);

            return course;
        }
    }


    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Long> {
        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Long doInBackground(Course... courses) {
            Long temp = courseDao.insertCourse(courses[0]);
            return temp;
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
