package main.java.com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.BookDTO;
import com.example.bookstoreapi.model.Book;
import com.example.bookstoreapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Operation(summary = "Create a new book")
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookDTO));
    }

    @Operation(summary = "Update an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @Operation(summary = "Delete a book by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all books")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
