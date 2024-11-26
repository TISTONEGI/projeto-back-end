package com.trabalhodeback.bibliotecadigital.model;

import lombok.Getter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;

@Getter
public enum UserRole {

    ADMIN("admin"),
    USER("user");

    private String role;
    UserRole(String role){
        this.role = role;
    }


}
