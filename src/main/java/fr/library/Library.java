package fr.library;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private final Map<String, Book> books = new LinkedHashMap<>();
    // private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            throw new DuplicateBookException("ISBN déjà présent : " + book.getIsbn());
        }
        books.put(book.getIsbn(), book);
    }

    public void removeBook(String isbn) {
        if (books.remove(isbn) == null) {
            throw new BookNotFoundException("Livre introuvable : " + isbn);
        }
    }

    public void displayBooks() {
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    // Recherche par titre
    public Book findBookByTitle(String title) {
        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        throw new BookNotFoundException("Livre introuvable : " + title);
    }

    // Recherche livres d'un auteur (renvoie une liste)
    public List<Book> findByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor() != null && book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    // Vérifie si l'ISBN est présent
    public boolean containsIsbn(String isbn) {
        return books.containsKey(isbn);
    }

    // Renvoie le nombre total de livres dans la bibliothèque
    public int size() {
        return books.size();
    }
}
