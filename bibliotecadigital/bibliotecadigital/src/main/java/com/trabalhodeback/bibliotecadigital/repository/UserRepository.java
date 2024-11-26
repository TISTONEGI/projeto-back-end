package com.trabalhodeback.bibliotecadigital.repository;

import com.trabalhodeback.bibliotecadigital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByNome(String nome);
    Optional<User> findByEmail(String email);
}
