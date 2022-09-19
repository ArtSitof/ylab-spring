package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.StorageImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private long id;
    private StorageImpl storage;


    public BookServiceImpl(StorageImpl storage) {
        this.storage = storage;
    }

    @Override
    public Book createBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(++id);
        book.setUserId(bookDto.getUserId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPageCount(bookDto.getPageCount());
        return book;
    }

    @Override
    public Book updateBook(BookDto bookDto) {

        return null;
    }

    @Override
    public Book getBookById(Long id) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {

    }
}
