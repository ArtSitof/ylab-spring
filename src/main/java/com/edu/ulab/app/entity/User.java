package com.edu.ulab.app.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private Long id;
    private String fullName;
    private String title;
    private int age;
    private List<Book> books;

    public void addBookToUser(Book book) {
        if(books == null) {
            books = new ArrayList<>();
        }
        this.books.add(book);
    }
}
