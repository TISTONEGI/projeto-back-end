package com.trabalhodeback.bibliotecadigital.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trabalhodeback.bibliotecadigital.dto.BookDto;
import com.trabalhodeback.bibliotecadigital.model.Book;
import com.trabalhodeback.bibliotecadigital.repository.BookRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/books")
@Tag(name = "book", description = "Endpoints for managing books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    @Secured("ROLE_ADMIN")
    @Operation(
            summary = "Create a new book",
            description = "Creates a new book entry in the database.",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);

        Book savedBook = bookRepository.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);

    }


    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Secured("ROLE_ADMIN")
    @Operation(
            summary = "Update a book by ID",
            description = "Updates an existing book entry by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody @Valid BookDto bookDto) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    BeanUtils.copyProperties(bookDto, existingBook, "id");
                    Book updatedBook = bookRepository.save(existingBook);
                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @Secured("ROLE_ADMIN")
    @Operation(
            summary = "Delete a book by ID",
            description = "Deletes a book entry from the database by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Operation(summary = "List all books with pagination")
    @GetMapping
    public ResponseEntity<Page<Book>> listBooks(@PageableDefault(size = 10) Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return ResponseEntity.ok(books);
    }
}
