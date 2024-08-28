package main.java.com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.CustomerDTO;
import com.example.bookstoreapi.model.Customer;
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
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private List<Customer> customers = new ArrayList<>();
    private Long customerIdCounter = 1L; // Simple counter for generating IDs

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAddress(customer.getAddress());

        // Adding HATEOAS links
        customerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerById(customerDTO.getId())).withSelfRel());
        customerDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getAllCustomers()).withRel("all-customers"));

        return customerDTO;
    }

    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        return customer;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(customerDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable @Min(1) Long id) {
        Customer customer = customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found"));  // Create a custom exception for customers if needed

        return new ResponseEntity<>(convertToDTO(customer), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody @Valid CustomerDTO newCustomerDTO) {
        Customer newCustomer = convertToEntity(newCustomerDTO);
        newCustomer.setId(customerIdCounter++); // Assign a new ID
        customers.add(newCustomer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerAdded");
        return new ResponseEntity<>(convertToDTO(newCustomer), headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable @Min(1) Long id, @RequestBody @Valid CustomerDTO updatedCustomerDTO) {
        Customer existingCustomer = customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Customer not found"));  // Create a custom exception for customers if needed

        existingCustomer.setName(updatedCustomerDTO.getName());
        existingCustomer.setEmail(updatedCustomerDTO.getEmail());
        existingCustomer.setAddress(updatedCustomerDTO.getAddress());

        return new ResponseEntity<>(convertToDTO(existingCustomer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable @Min(1) Long id) {
        boolean removed = customers.removeIf(customer -> customer.getId().equals(id));

        if (!removed) {
            throw new RuntimeException("Customer not found");  // Create a custom exception for customers if needed
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomerDeleted");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
