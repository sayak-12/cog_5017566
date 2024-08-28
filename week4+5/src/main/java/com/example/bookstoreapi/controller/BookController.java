package main.java.com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.exception.BookNotFoundException;
import com.example.bookstoreapi.model.Book;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    private List<Book> books = new ArrayList<>();
    private Long bookIdCounter = 1L; // Simple counter for generating IDs

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setIsbn(book.getIsbn());

        // Adding HATEOAS links
        bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel());
        bookDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("all-books"));

        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());
        book.setIsbn(bookDTO.getIsbn());
        return book;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookDTOs = books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable @Min(1) Long id) {
        Book book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));

        return new ResponseEntity<>(convertToDTO(book), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookDTO newBookDTO) {
        Book newBook = convertToEntity(newBookDTO);
        newBook.setId(bookIdCounter++); // Assign a new ID
        books.add(newBook);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookAdded");
        return new ResponseEntity<>(convertToDTO(newBook), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable @Min(1) Long id, @RequestBody @Valid BookDTO updatedBookDTO) {
        Book existingBook = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(id));

        existingBook.setTitle(updatedBookDTO.getTitle());
        existingBook.setAuthor(updatedBookDTO.getAuthor());
        existingBook.setPrice(updatedBookDTO.getPrice());
        existingBook.setIsbn(updatedBookDTO.getIsbn());

        return new ResponseEntity<>(convertToDTO(existingBook), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable @Min(1) Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));

        if (!removed) {
            throw new BookNotFoundException(id);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookDeleted");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
