package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity;

public class Book {

    private int bookId;
    private String title;
    private String imageUrl;
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
