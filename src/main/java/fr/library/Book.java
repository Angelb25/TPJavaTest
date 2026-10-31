package fr.library;

public class Book {

    private final String isbn;
    private String title;
    private String author;
    private int year;

    public Book(String isbn, String title, String author, int year) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("L'ISBN ne peut pas être null, vide ou composé d'espaces.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Le titre ne peut pas être null, vide ou composé d'espaces.");
        }
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return "Book{isbn='" + isbn + "', title='" + title + "', author='" + author + "', year=" + year + '}';
    }
}