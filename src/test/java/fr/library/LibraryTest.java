package fr.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    //  addBook

    @Test
    void shouldAddBook() {
        library.addBook(new Book("123", "Clean Code", "Robert C. Martin", 2008));

        assertEquals(1, library.size());
    }

    @Test
    void shouldThrowWhenIsbnAlreadyExists() {
        library.addBook(new Book("123", "Clean Code", "Robert C. Martin", 2008));

        DuplicateBookException e = assertThrows(DuplicateBookException.class,
                () -> library.addBook(new Book("123", "Autre", "Autre", 2020)));

        assertEquals("ISBN déjà présent : 123", e.getMessage());
        assertEquals(1, library.size());
    }

    //  removeBook

    @Test
    void shouldRemoveBook() {
        library.addBook(new Book("123", "Clean Code", "Robert C. Martin", 2008));

        library.removeBook("123");

        assertEquals(0, library.size());
        assertFalse(library.containsIsbn("123"));
    }

    @Test
    void shouldThrowWhenRemovingUnknownIsbn() {
        library.addBook(new Book("123", "Clean Code", "Robert C. Martin", 2008));

        assertThrows(BookNotFoundException.class, () -> library.removeBook("inconnu"));
    }

    @Test
    void shouldThrowWhenRemovingFromEmptyLibrary() {
        assertThrows(BookNotFoundException.class, () -> library.removeBook("123"));
    }

    //  findBookByTitle

    @Test
    void shouldFindBookByTitle() {
        library.addBook(new Book("9780132350884", "Clean Code", "Robert C. Martin", 2008));

        Book book = library.findBookByTitle("Clean Code");

        assertEquals("9780132350884", book.getIsbn());
    }

    @Test
    void shouldFindBookByTitleIgnoringCase() {
        library.addBook(new Book("123", "Clean Code", "Robert C. Martin", 2008));

        Book book = library.findBookByTitle("clean code");

        assertEquals("123", book.getIsbn());
    }

    @Test
    void shouldThrowWhenTitleNotFound() {
        library.addBook(new Book("123", "Clean Code", "Robert C. Martin", 2008));

        assertThrows(BookNotFoundException.class, () -> library.findBookByTitle("Inconnu"));
    }

    @Test
    void shouldThrowWhenSearchingInEmptyLibrary() {
        assertThrows(BookNotFoundException.class, () -> library.findBookByTitle("Clean Code"));
    }

    // findByAuthor

    @Test
    void shouldFindAllBooksOfAnAuthor() {
        library.addBook(new Book("1", "Clean Code", "Robert Martin", 2008));
        library.addBook(new Book("2", "Clean Architecture", "Robert Martin", 2017));
        library.addBook(new Book("3", "Effective Java", "Joshua Bloch", 2018));

        List<Book> result = library.findByAuthor("robert martin");

        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnEmptyListWhenAuthorUnknown() {
        library.addBook(new Book("1", "Clean Code", "Robert Martin", 2008));

        assertTrue(library.findByAuthor("Inconnu").isEmpty());
    }

    // Isbn / size

    @Test
    void shouldContainIsbnOfAddedBook() {
        library.addBook(new Book("123", "Clean Code", "Robert C. Martin", 2008));

        assertTrue(library.containsIsbn("123"));
        assertFalse(library.containsIsbn("999"));
    }

    @Test
    void shouldHaveSizeZeroWhenEmpty() {
        assertEquals(0, library.size());
    }

    // hiérarchie d'exceptions

    @Test
    void exceptionsShouldBeLibraryExceptions() {
        assertTrue(LibraryException.class.isAssignableFrom(BookNotFoundException.class));
        assertTrue(LibraryException.class.isAssignableFrom(DuplicateBookException.class));
    }

    @Test
    void shouldReturnEmptyListWhenSortingEmptyLibrary() {
        assertTrue(library.booksSortedByTitle().isEmpty());
    }

    @Test
    void shouldKeepBothBooksWithSameTitle() {
        library.addBook(new Book("2", "Clean Code", "B", 2008));
        library.addBook(new Book("1", "Clean Code", "A", 2010));

        List<Book> result = library.booksSortedByTitle();

        assertEquals(2, result.size());
    }

    @Test
    void shouldSortByTitle() {
        library.addBook(new Book("1", "Zebra", "A", 2000));
        library.addBook(new Book("2", "Alpha", "B", 2001));

        List<Book> result = library.booksSortedByTitle();

        assertEquals("Alpha", result.get(0).getTitle());
        assertEquals("Zebra", result.get(1).getTitle());
    }
    static Stream<Arguments> criteriaProvider() {
        return Stream.of(
                Arguments.of(new ByTitle("clean code"), List.of("1")),
                Arguments.of(new ByAuthor("robert martin"), List.of("1", "2")),
                Arguments.of(new ByYearRange(2010, 2020), List.of("3")),
                Arguments.of(new ByTitle("Inconnu"), List.of())
        );
    }

    @ParameterizedTest
    @MethodSource("criteriaProvider")
    void shouldSearchByCriteria(SearchCriteria criteria, List<String> expectedIsbns) {
        library.addBook(new Book("1", "Clean Code", "Robert Martin", 2008));
        library.addBook(new Book("2", "Clean Architecture", "Robert Martin", 2017));
        library.addBook(new Book("3", "Effective Java", "Joshua Bloch", 2018));

        List<String> isbns = library.search(criteria).stream().map(Book::getIsbn).toList();

        assertEquals(expectedIsbns, isbns);
    }
}