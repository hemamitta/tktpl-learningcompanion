package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Book;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Course;


@Dao
public interface BookDao {

    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Query("SELECT * FROM book_table")
    LiveData<List<Book>> getAllBooks();
}
