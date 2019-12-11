package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.R;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Course;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.databinding.CourseAdapter;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.viewmodel.CourseViewModel;

public class LearningProgressActivity extends MainActivity {
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;

    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_progress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.learningProgress);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Add Course
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LearningProgressActivity.this, AddCourseActivity.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }
        });

        // List of Courses
        RecyclerView recyclerView = findViewById(R.id.course_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {
                adapter.setCourses(courses);
            }
        });

        // Update
        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(LearningProgressActivity.this, CourseProgressActivity.class);
                intent.putExtra(CourseProgressActivity.EXTRA_COURSE_ID, course.getCourseId());
                intent.putExtra(CourseProgressActivity.EXTRA_COURSE_NAME, course.getName());
                intent.putExtra(CourseProgressActivity.EXTRA_TARGET_TIME, course.getTargetTime());
                intent.putExtra(CourseProgressActivity.EXTRA_TOTAL_TIME, course.getTotalTime());
                startActivityForResult(intent, EDIT_COURSE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
            String courseName = data.getStringExtra(AddCourseActivity.EXTRA_COURSE_NAME);
            int targetMinute = data.getIntExtra(AddCourseActivity.EXTRA_TARGET_TIME, 150);
            int totalTime = data.getIntExtra(AddCourseActivity.EXTRA_TOTAL_TIME, 0);

            Course course = new Course(courseName, targetMinute, totalTime);
            courseViewModel.insert(course);

            Toast.makeText(this, R.string.courseSaved, Toast.LENGTH_SHORT).show();
        }

        else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(CourseProgressActivity.EXTRA_COURSE_ID, -1);

            if (id == -1) {
                Toast.makeText(this, R.string.courseCantBeUpdated, Toast.LENGTH_SHORT).show();
                return;
            }

            String courseName = data.getStringExtra(AddCourseActivity.EXTRA_COURSE_NAME);
            int targetMinute = data.getIntExtra(AddCourseActivity.EXTRA_TARGET_TIME, 150);
            int totalTime = data.getIntExtra(AddCourseActivity.EXTRA_TOTAL_TIME, 0);

            Course course = new Course(courseName, targetMinute, totalTime);
            course.setCourseId(id);
            courseViewModel.update(course);

            Toast.makeText(this, R.string.courseProgressUpdated, Toast.LENGTH_SHORT).show();
        }

        else if (requestCode == ADD_COURSE_REQUEST) {
            Toast.makeText(this, R.string.courseNotSaved, Toast.LENGTH_SHORT).show();
        }

    }
}
