package com.trabalhodeback.bibliotecadigital.controller;

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

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Endpoint para criar um novo livro.
     * Apenas administradores podem acessar este endpoint.
     */
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Create a new book")
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookDto bookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDto, book);
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    /**
     * Endpoint para buscar um livro pelo ID.
     * Qualquer usuário autenticado pode acessar.
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Operation(summary = "Retrieve a book by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Endpoint para atualizar um livro pelo ID.
     * Apenas administradores podem acessar este endpoint.
     */
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Update a book by ID")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody @Valid BookDto bookDto) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    BeanUtils.copyProperties(bookDto, existingBook, "id");
                    Book updatedBook = bookRepository.save(existingBook);
                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Endpoint para deletar um livro pelo ID.
     * Apenas administradores podem acessar este endpoint.
     */
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Delete a book by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Endpoint para listar livros com paginação.
     * Qualquer usuário autenticado pode acessar.
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Operation(summary = "List all books with pagination")
    @GetMapping
    public ResponseEntity<Page<Book>> listBooks(@PageableDefault(size = 10) Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return ResponseEntity.ok(books);
    }
}
