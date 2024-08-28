
package main.java.com.example.bookstoreapi.service;

import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetBookById() {
        MockitoAnnotations.openMocks(this);
        Book book = new Book(1, "Effective Java", "Joshua Bloch", 45.00, "978-0134685991");
        when(bookRepository.findById(1)).thenReturn(java.util.Optional.of(book));

        Book result = bookService.getBookById(1);
        assertEquals("Effective Java", result.getTitle());
    }

    @Test
    public void testCreateBook() {
        Book book = new Book(1, "Effective Java", "Joshua Bloch", 45.00, "978-0134685991");
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.createBook(book);
        assertEquals("Effective Java", result.getTitle());
    }
}
