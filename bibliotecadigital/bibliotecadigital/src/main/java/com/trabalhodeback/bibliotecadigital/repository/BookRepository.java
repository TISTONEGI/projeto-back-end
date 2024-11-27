package com.trabalhodeback.bibliotecadigital.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabalhodeback.bibliotecadigital.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    

    Optional<Book> findByTitulo(String titulo);

   
    boolean existsByTitulo(String titulo);


}
