package com.trabalhodeback.bibliotecadigital.exception;

public class BookNotFoundException extends RuntimeException{

   public BookNotFoundException(){ super("Livro não existe!"); }

}
