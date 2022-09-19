package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.BookAlreadyExistsException;
import com.edu.ulab.app.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StorageImpl implements Storage{
    private final static Map<Long, User> users = new HashMap<>();
    private final static Map<Long, Book> books = new HashMap<>();

    @Override
    public void addUserToStorage(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User getUserById(Long id) {
        if (id != null && users.containsKey(id) && id > 0) {
            return users.get(id);
        } else {
            throw new NotFoundException("Пользователь не найден!");
        }
    }

    @Override
    public User updateUser(Long id, User user) {
        return users.put(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        if (id != null && users.containsKey(id) && id > 0){
            users.remove(id);
        } else {
            throw new NotFoundException("Не удается удалить пользователя, т.к. его не существует!");
        }
    }

    @Override
    public void addBookToStorage(Book book) {
        if (book != null && !books.containsValue(book)){
            books.put(book.getId(), book);
        } else {
            throw new BookAlreadyExistsException("Книга уже добавлена!");
        }
    }
}
