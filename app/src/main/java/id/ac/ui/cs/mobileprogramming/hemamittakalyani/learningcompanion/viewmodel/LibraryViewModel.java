package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Library;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.repository.LibraryRepository;

public class LibraryViewModel extends AndroidViewModel {
    private LibraryRepository repository;
    private LiveData<List<Library>> allLibs;

    public LibraryViewModel(@NonNull Application application) {
        super(application);
        repository = new LibraryRepository(application);
        allLibs = repository.getAllLibs();
    }

    public void insert(Library library) {
        repository.insert(library);
    }

    public void update(Library library) {
        repository.update(library);
    }

    public LiveData<List<Library>> getAllLibs() {
        return allLibs;
    }
}
