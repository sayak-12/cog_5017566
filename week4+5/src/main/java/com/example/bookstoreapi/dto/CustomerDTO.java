package main.java.com.example.bookstoreapi.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CustomerDTO extends RepresentationModel<CustomerDTO> {
    private Long id;
    private String name;
    private String email;
    private String address;
}
