package fr.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    private static final String BOOK_JSON = """
            {"isbn": "123", "title": "Clean Code", "author": "Robert Martin", "year": 2008}
            """;

    @Test
    void shouldReturnBooksSortedByTitle() throws Exception {
        given(bookService.getBooks("title")).willReturn(List.of(
                new Book("1", "Alpha", "A", 2000),
                new Book("2", "Zebra", "B", 2001)));

        mockMvc.perform(get("/books").param("sort", "title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Alpha"))
                .andExpect(jsonPath("$[1].title").value("Zebra"));
    }

    @Test
    void shouldCreateBook() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BOOK_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("123"));
    }

    @Test
    void shouldReturn409WhenIsbnAlreadyExists() throws Exception {
        willThrow(new DuplicateBookException("ISBN déjà présent : 123"))
                .given(bookService).addBook(any(Book.class));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BOOK_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.detail").value("ISBN déjà présent : 123"));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/books/123"))
                .andExpect(status().isNoContent());

        verify(bookService).removeBook("123");
    }

    @Test
    void shouldReturn404WhenBookNotFound() throws Exception {
        willThrow(new BookNotFoundException("Livre introuvable : 999"))
                .given(bookService).removeBook("999");

        mockMvc.perform(delete("/books/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Livre introuvable : 999"));
    }
}