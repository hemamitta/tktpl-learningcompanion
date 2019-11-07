package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.dao.LibraryDao;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Library;

@Database(entities = {Library.class}, version = 1, exportSchema = false)
public abstract class LibraryDatabase extends RoomDatabase {

    private static LibraryDatabase INSTANCE;

    public abstract LibraryDao libraryDao();

    public static synchronized LibraryDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LibraryDatabase.class, "library_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
      private LibraryDao libraryDao;

      private PopulateDbAsyncTask(LibraryDatabase db) {
          libraryDao = db.libraryDao();
      }

        @Override
        protected Void doInBackground(Void... voids) {
          JSONObject daa1 = new JSONObject();
          try {
              daa1.put("title", "CLRS. (2009). Introduction to Algorithms");
              daa1.put("imageUrl", "image");
              daa1.put("pdfUrl", "pdf");
          }
          catch (JSONException e) {
              e.printStackTrace();
          }

          JSONObject daa2 = new JSONObject();
          try {
              daa1.put("title", "Erickson, J. (2019). Algorithm");
              daa1.put("imageUrl", "Erickson, J. (2019). Algorithm");
              daa1.put("pdfUrl", "Erickson, J. (2019). Algorithm");
          }
          catch (JSONException e) {
              e.printStackTrace();
          }

          List<JSONObject> daaBookList = new ArrayList<JSONObject>();
          daaBookList.add(daa1);
          daaBookList.add(daa2);

          libraryDao.insert(new Library("Design and Analytics Algorithm", daaBookList));
          return null;
        }
    }
}
