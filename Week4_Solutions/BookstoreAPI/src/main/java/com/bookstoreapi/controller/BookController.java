package com.bookstoreapi.controller;

import com.bookstoreapi.dto.BookDTO;
import com.bookstoreapi.service.BookService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<BookDTO>> getBookById(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
        Optional<BookDTO> bookDTO = bookService.getBookById(id);

        if (bookDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<BookDTO> bookResource = EntityModel.of(bookDTO.get());
        bookResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(id, headers)).withSelfRel());
        bookResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("books"));

        return ResponseEntity.ok(bookResource);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooks() {
        List<EntityModel<BookDTO>> books = bookService.getAllBooks().stream()
                .map(bookDTO -> {
                    EntityModel<BookDTO> bookResource = EntityModel.of(bookDTO);
                    bookResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDTO.getId(), new HttpHeaders())).withSelfRel());
                    return bookResource;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(books, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withSelfRel()));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<BookDTO>> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        EntityModel<BookDTO> bookResource = EntityModel.of(createdBook);

        bookResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(createdBook.getId(), new HttpHeaders())).withSelfRel());
        bookResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("books"));

        return ResponseEntity.created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(createdBook.getId(), new HttpHeaders())).toUri())
                             .body(bookResource);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<BookDTO>> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        EntityModel<BookDTO> bookResource = EntityModel.of(updatedBook);

        bookResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(id, new HttpHeaders())).withSelfRel());
        bookResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("books"));

        return ResponseEntity.ok(bookResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
