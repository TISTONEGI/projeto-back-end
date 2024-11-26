package com.trabalhodeback.bibliotecadigital.repository;

import com.trabalhodeback.bibliotecadigital.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}
