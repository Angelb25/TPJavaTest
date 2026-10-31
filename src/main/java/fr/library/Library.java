package fr.library;

import java.util.*;
import java.util.stream.*;

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
        books.values().forEach(System.out::println);
    }

    // Recherche par titre
    public Book findBookByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Livre introuvable : " + title));
    }

    // Recherche livres d'un auteur (renvoie une liste)
    public List<Book> findByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor() != null && book.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    public List<Book> booksSortedByTitle() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
    }

    public List<Book> booksSortedByAuthor() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle))
                .toList();
    }

    public List<Book> booksSortedByIsbn() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::getIsbn))
                .toList();
    }

    // Vérifie si l'ISBN est présent
    public boolean containsIsbn(String isbn) {
        return books.containsKey(isbn);
    }

    // Renvoie le nombre total de livres dans la bibliothèque
    public int size() {
        return books.size();
    }

    public List<Book> search(SearchCriteria criteria) {
        return books.values().stream()
                .filter(book -> switch (criteria) {
                    case ByTitle(String title) -> book.getTitle().equalsIgnoreCase(title);
                    case ByAuthor(String author) ->
                            book.getAuthor() != null && book.getAuthor().equalsIgnoreCase(author);
                    case ByYearRange(int from, int to) ->
                            book.getYear() >= from && book.getYear() <= to;
                })
                .toList();
    }

}
