package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.dao.CourseDao;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Course;

@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase {

    private static CourseDatabase INSTANCE;

    public abstract CourseDao courseDao();

    public static synchronized CourseDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    CourseDatabase.class, "course_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
      private CourseDao courseDao;

      private PopulateDbAsyncTask(CourseDatabase db) {
          courseDao = db.courseDao();
      }

        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.insert(new Course("Mobile Programming", 9000, 0));
            courseDao.insert(new Course("Interaction System", 9000, 0));
            return null;
        }
    };
}
