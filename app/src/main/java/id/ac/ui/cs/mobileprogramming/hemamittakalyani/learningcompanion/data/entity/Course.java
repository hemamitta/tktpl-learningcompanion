package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    @ColumnInfo(name = "courseName")
    private String name;

    @ColumnInfo(name = "courseTargetTime")
    private int targetTime;

    @ColumnInfo(name = "courseTotalTime")
    private int totalTime;

    public Course(String name, int targetTime, int totalTime) {
        this.name = name;
        this.targetTime = targetTime;
        this.totalTime = totalTime;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public int getTargetTime() {
        return targetTime;
    }

    public int getTotalTime() {
        return totalTime;
    }
}
