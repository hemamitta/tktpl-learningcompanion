package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.dao.CourseDao;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Course;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.database.CourseDatabase;

public class CourseRepository {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;

    public CourseRepository(Application application) {
        CourseDatabase database = CourseDatabase.getInstance(application);
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();
    }

    public void insert(Course course) {
        new InsertCourseAsyncTask(courseDao).execute(course);
    }

    public void update(Course course) {
        new UpdateCourseAsyncTask(courseDao).execute(course);
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private  CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insert(courses[0]);
            return null;
        }
    }

    public static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private  CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.update(courses[0]);
            return null;
        }
    }
}
