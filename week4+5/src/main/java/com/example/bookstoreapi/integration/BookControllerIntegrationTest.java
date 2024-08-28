package main.java.com.example.bookstoreapi.integration;

import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetBook() throws Exception {
        Book book = new Book(1, "Effective Java", "Joshua Bloch", 45.00, "978-0134685991");

        // Create a new book
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Effective Java"));

        // Retrieve the created book
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book(1, "Effective Java", "Joshua Bloch", 45.00, "978-0134685991");
        bookRepository.save(book);

        Book updatedBook = new Book(1, "Effective Java, 3rd Edition", "Joshua Bloch", 50.00, "978-0134685991");

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java, 3rd Edition"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book(1, "Effective Java", "Joshua Bloch", 45.00, "978-0134685991");
        bookRepository.save(book);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }
}
