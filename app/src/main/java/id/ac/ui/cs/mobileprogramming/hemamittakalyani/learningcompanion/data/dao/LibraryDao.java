package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Library;


@Dao
public interface LibraryDao {

    @Insert
    void insert(Library library);

    @Update
    void update(Library library);

    @Query("SELECT * FROM library_table")
    LiveData<List<Library>> getAllLibrary();
}
