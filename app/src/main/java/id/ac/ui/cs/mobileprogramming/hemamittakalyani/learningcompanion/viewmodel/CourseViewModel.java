package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Course;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.repository.CourseRepository;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepository repository;
    private LiveData<List<Course>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
        allCourses = repository.getAllCourses();
    }

    public void insert(Course course) {
        repository.insert(course);
    }

    public void update(Course course) {
        repository.update(course);
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

}
