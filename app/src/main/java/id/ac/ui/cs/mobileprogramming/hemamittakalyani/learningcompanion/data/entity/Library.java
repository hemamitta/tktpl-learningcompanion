package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views.BookTypeConverter;

@Entity(tableName = "library_table")
public class Library {

    @PrimaryKey(autoGenerate = true)
    private int libraryId;

    @ColumnInfo(name = "libraryName")
    private String libraryName;

    @TypeConverters(BookTypeConverter.class)
    @ColumnInfo(name = "bookList")
    private List<Book> bookList;

    public Library(String libraryName, List<Book> bookList) {
        this.libraryName = libraryName;
        this.bookList = bookList;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
