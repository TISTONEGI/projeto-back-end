package com.trabalhodeback.bibliotecadigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabalhodeback.bibliotecadigital.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    

    Optional<Book> findByTitle(String titulo);

   
    boolean existsByTitle(String titulo);
}
