package fr.library;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookTest {
    @Test
    // Livre cree
    void shouldCreateBookWithValidData() {
        Book book = new Book("9780132350884", "Clean Code", "Robert C. Martin", 2008);

        assertEquals("9780132350884", book.getIsbn());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert C. Martin", book.getAuthor());
        assertEquals(2008, book.getYear());
    }

    // Isbn invalide
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void shouldRejectInvalidIsbn(String isbn) {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(isbn, "Titre", "Auteur", 2020));
    }

    // titre invalide
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void shouldRejectInvalidTitle(String title) {
        assertThrows(IllegalArgumentException.class,
                () -> new Book("123", title, "Auteur", 2020));
    }
}
