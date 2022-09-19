package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;

public interface BookService {
    Book createBook(BookDto userDto);

    Book updateBook(BookDto userDto);

    Book getBookById(Long id);

    void deleteBookById(Long id);
}
