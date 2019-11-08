package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.dao.LibraryDao;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Book;
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
          Book daa_book_1 = new Book("CLRS - Introduction to Algorithms",
                  "https://p.calameoassets.com/110901104257-f47e4def8b758fe722071ee0c0afdfb6/p1.jpg",
                  "http://kddlab.zjgsu.edu.cn:7200/students/lipengcheng/%E7%AE%97%E6%B3%95%E5%AF%BC%E8%AE%BA%EF%BC%88%E8%8B%B1%E6%96%87%E7%AC%AC%E4%B8%89%E7%89%88%EF%BC%89.pdf"
          );

          Book daa_book_2 = new Book("Jeff Erickson - Algorithm",
                  "http://jeffe.cs.illinois.edu/teaching/algorithms/FrontCover.png",
                  "http://jeffe.cs.illinois.edu/teaching/algorithms/book/Algorithms-JeffE.pdf");

          Book daa_book_3 = new Book("Tutorials Point - Design and Analysis of Algorithm",
                  "http://jeffe.cs.illinois.edu/teaching/algorithms/FrontCover.png",
                  "https://www.tutorialspoint.com/design_and_analysis_of_algorithms/design_and_analysis_of_algorithms_tutorial.pdf");

          Book daa_book_4 = new Book("Dexter Kozen - The Design and Analysis of Algorithms",
                  "http://jeffe.cs.illinois.edu/teaching/algorithms/FrontCover.png",
                  "https://www.cs.cornell.edu/~kozen/Papers/daa.pdf");

          List<Book> daaBookList = new ArrayList<Book>();
          daaBookList.add(daa_book_1);
          daaBookList.add(daa_book_2);
          daaBookList.add(daa_book_3);
          daaBookList.add(daa_book_4);

          libraryDao.insert(new Library("Design and Analytics Algorithm", daaBookList));
          return null;
        }
    }
}
