package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Course;


@Dao
public interface CourseDao {

    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Query("SELECT * FROM course_table")
    LiveData<List<Course>> getAllCourses();
}
