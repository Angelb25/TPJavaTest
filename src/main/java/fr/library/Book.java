package fr.library;

public class Book {
    // TODO 2 : Créer les getters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return Isbn;
    }

    // TODO 3 : Redéfinir toString()
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", Isbn=" + Isbn +
                '}';
    }

    private String title;
    private String author;
    private int year;
    private String Isbn;

    public Book(String title, String author, int year, String Isbn) {
        // TODO 1 : initialiser les champs
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre du livre ne peut pas être null ou vide.");
        }

        if (Isbn == null || Isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ISBN du livre ne peut pas être null ou vide.");
        }
        this.author = author;
        this.title = title;
        this.year = year;
        this.Isbn = Isbn;
    }
}
