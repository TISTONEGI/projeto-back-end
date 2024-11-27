package com.trabalhodeback.bibliotecadigital.usecase;

import com.trabalhodeback.bibliotecadigital.exception.BookNotFoundException;
import com.trabalhodeback.bibliotecadigital.model.Book;
import com.trabalhodeback.bibliotecadigital.repository.BookRepository;
import com.trabalhodeback.bibliotecadigital.service.GetBookByIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class GetBookByIdUseCaseTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private GetBookByIdUseCase getBookByIdUseCase;

    @Test
    void execute() {
        UUID uuid = UUID.randomUUID();
        Book book = new Book();
        book.setId(uuid);
        book.setTitulo("O Senhor dos Anéis");
        book.setAutor("J.R.R. Tolkien");
        book.setGenero("Fantasia");
        book.setAnoPublicacao(1954);
        book.setNumeroExemplares(5);

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        Book bookReturned = getBookByIdUseCase.execute(uuid);

        assertThat(bookReturned.getId()).isEqualTo(uuid);
    }

    @Test
    void execute2() {
        UUID uuid = UUID.randomUUID();
        Book book = new Book();
        book.setId(uuid);
        book.setTitulo("O Senhor dos Anéis");
        book.setAutor("J.R.R. Tolkien");
        book.setGenero("Fantasia");
        book.setAnoPublicacao(1954);
        book.setNumeroExemplares(5);

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        getBookByIdUseCase.execute(uuid);

        verify(bookRepository, times(1)).findById(any());
    }

    @Test
    void execute3() {
        UUID uuid = UUID.randomUUID();
        Book book = new Book();
        book.setId(uuid);
        book.setTitulo("O Senhor dos Anéis");
        book.setAutor("J.R.R. Tolkien");
        book.setGenero("Fantasia");
        book.setAnoPublicacao(1954);
        book.setNumeroExemplares(5);

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        assertDoesNotThrow(() -> getBookByIdUseCase.execute(uuid));
    }

    @Test
    void executeWithException() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> getBookByIdUseCase.execute(UUID.randomUUID()));
    }
}

