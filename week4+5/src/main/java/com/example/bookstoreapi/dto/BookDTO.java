package main.java.com.example.bookstoreapi.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class BookDTO extends RepresentationModel<BookDTO> {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private String isbn;
}
