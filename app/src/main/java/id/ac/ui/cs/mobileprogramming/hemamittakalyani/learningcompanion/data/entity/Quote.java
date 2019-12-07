package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity;

public class Quote {
    private String author;
    private String content;

    public String getQuote() {
        return content;
    }

    public void setQuote(String quote) {
        this.content = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
