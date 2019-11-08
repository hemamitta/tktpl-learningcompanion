package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "book_table")
public class Book implements Serializable {

    @SerializedName("bookId")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private int bookId;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("imageUrl")
    @Expose
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    @SerializedName("pdfUrl")
    @Expose
    @ColumnInfo(name = "pdfUrl")
    private String pdfUrl;

    public Book(String title, String imageUrl, String pdfUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.pdfUrl = pdfUrl;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
