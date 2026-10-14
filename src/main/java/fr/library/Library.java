package fr.library;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Recherche par titre
    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Recherche livres d'un auteur (renvoie une liste)
    public List<Book> findByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    // Vérifie si l'ISBN est présent
    public boolean containsIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    // Renvoie le nombre total de livres dans la bibliothèque
    public int size() {
        return books.size();
    }
}
