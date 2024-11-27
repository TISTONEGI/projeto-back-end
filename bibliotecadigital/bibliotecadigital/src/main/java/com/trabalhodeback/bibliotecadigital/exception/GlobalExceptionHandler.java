package com.trabalhodeback.bibliotecadigital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;


@ControllerAdvice
    public class GlobalExceptionHandler {

        // Tratamento para BookNotFoundException
        @ExceptionHandler(BookNotFoundException.class)
        public ResponseEntity<ApiError> handleBookNotFoundException(BookNotFoundException ex) {
            ApiError apiError = new ApiError();
            apiError.setCode("Book-001");
            apiError.setStatus(404);
            apiError.setMessage("Livro não encontrado: " + ex.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        }

        // Tratamento para UserNotFoundException
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex) {
            ApiError apiError = new ApiError();
            apiError.setCode("User-001");
            apiError.setStatus(404);
            apiError.setMessage("Usuário não encontrado: " + ex.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        }

        // Tratamento para AccessDeniedException (403)
        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
            ApiError apiError = new ApiError();
            apiError.setCode("Access-403");
            apiError.setStatus(403);
            apiError.setMessage("Acesso negado: " + ex.getMessage());

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
        }

        // Tratamento para erros de autenticação (401)
        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex) {
            ApiError apiError = new ApiError();
            apiError.setCode("Auth-401");
            apiError.setStatus(401);
            apiError.setMessage("Não autorizado: " + ex.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
        }



        // Tratamento para erros internos do servidor (500)
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> handleGenericException(Exception ex) {
            ApiError apiError = new ApiError();
            apiError.setCode("Server-500");
            apiError.setStatus(500);
            apiError.setMessage("Erro interno do servidor: " + ex.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
        }
    }







