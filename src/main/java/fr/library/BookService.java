package fr.library;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {

    private final Library library = new Library();

    public BookService() {
        library.addBook(new Book("9780132350884", "Clean Code", "Robert Martin", 2008));
        library.addBook(new Book("9780134685991", "Effective Java", "Joshua Bloch", 2018));
    }
    public List<Book> getBooks(String sort) {
        return switch (sort) {
            case "title" -> library.booksSortedByTitle();
            case "author" -> library.booksSortedByAuthor();
            case "isbn" -> library.booksSortedByIsbn();
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Tri inconnu : " + sort);
        };
    }

    public void addBook(Book book) {
        library.addBook(book);
    }

    public void removeBook(String isbn) {
        library.removeBook(isbn);
    }
}