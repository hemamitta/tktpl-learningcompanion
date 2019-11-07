package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONObject;

import java.util.List;

@Entity(tableName = "library_table")
public class Library {

    @PrimaryKey(autoGenerate = true)
    private int libraryId;

    @ColumnInfo(name = "libraryName")
    private String libraryName;

    @ColumnInfo(name = "bookList")
    private List<JSONObject> bookList;

    public Library(String libraryName, List<JSONObject> bookList) {
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

    public List<JSONObject> getBookList() {
        return bookList;
    }

    public void setBookList(List<JSONObject> bookList) {
        this.bookList = bookList;
    }
}
