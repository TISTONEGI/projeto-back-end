package com.trabalhodeback.bibliotecadigital.service;

import com.trabalhodeback.bibliotecadigital.exception.BookNotFoundException;
import com.trabalhodeback.bibliotecadigital.model.Book;
import com.trabalhodeback.bibliotecadigital.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetBookByIdUseCase {

   @Autowired
    private BookRepository bookRepository;

    public Book execute(UUID id) {
        Optional<Book> optionalBook = bookRepository.findById();

        if(optionalBook.isEmpty()){
            throw new BookNotFoundException();
        }

        return optionalBook.get();
    }


}
