package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.dao.LibraryDao;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Library;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.database.LibraryDatabase;

public class LibraryRepository {
    private LibraryDao libraryDao;
    private LiveData<List<Library>> allLibs;

    public LibraryRepository(Application application) {
        LibraryDatabase database = LibraryDatabase.getInstance(application);
        libraryDao = database.libraryDao();
        allLibs = libraryDao.getAllLibrary();
    }

    public void insert(Library library) {
        new InsertLibraryAsyncTask(libraryDao).execute(library);
    }

    public void update(Library library) {
        new UpdateLibraryAsyncTask(libraryDao).execute(library);
    }

    public LiveData<List<Library>> getAllLibs() {
        return allLibs;
    }

    public static class InsertLibraryAsyncTask extends AsyncTask<Library, Void, Void> {
        private  LibraryDao libraryDao;

        private InsertLibraryAsyncTask(LibraryDao libraryDao) {
            this.libraryDao = libraryDao;
        }

        @Override
        protected Void doInBackground(Library... libraries) {
            libraryDao.insert(libraries[0]);
            return null;
        }
    }

    public static class UpdateLibraryAsyncTask extends AsyncTask<Library, Void, Void> {
        private  LibraryDao libraryDao;

        private UpdateLibraryAsyncTask(LibraryDao libraryDao) {
            this.libraryDao = libraryDao;
        }

        @Override
        protected Void doInBackground(Library... libraries) {
            libraryDao.update(libraries[0]);
            return null;
        }
    }

}
