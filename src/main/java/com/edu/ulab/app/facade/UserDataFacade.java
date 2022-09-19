package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.Storage;
import com.edu.ulab.app.storage.StorageImpl;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.request.UserRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {


    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private StorageImpl storage;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper,
                          StorageImpl storage) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
        this.storage = storage;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        User createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
//                .stream()
//                .filter(Objects::nonNull)
//                .map(bookMapper::bookRequestToBookDto)
//                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
//                .peek(mappedBook -> log.info("mapped book: {}", mappedBook))
//                .map(bookService::createBook)
//                .peek(createdBook -> log.info("Created book: {}", createdBook))
//                .map(Book::getId)
//                .toList();
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBook -> log.info("mapped book: {}", mappedBook))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook.toString()))
                .peek(book -> storage.addBookToStorage(book))
                .peek(createdUser::addBookToUser)
                .map(Book::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long id) {
        log.info("Got user book create request: {}", userBookRequest);
        User userById = userService.getUserById(id);
        log.info("Got user from storage : {}", userById);
        UserRequest userRequest = userBookRequest.getUserRequest();
        log.info("Got user request: {}", userRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userRequest);
        log.info("Got userDto from request: {}", userDto);
        userById.setFullName(userDto.getFullName());
        userById.setTitle(userDto.getTitle());
        userById.setAge(userDto.getAge());
        userById.setBooks(null);
        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(id))
                .peek(mappedBook -> log.info("mapped book: {}", mappedBook))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .peek(userById::addBookToUser)
                .map(Book::getId)
                .toList();
        log.info("Updated user : {}", userById);
        return UserBookResponse.builder()
                .userId(userById.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        User user = userService.getUserById(userId);
        log.info("Got user from storage : {}", user);
        List<Book> books = user.getBooks();
        List<Long> bookIdList = books.stream()
                .map(Book::getId)
                .toList();
        return UserBookResponse.builder()
                .userId(user.getId())
                .booksIdList((bookIdList))
                .build();
    }

    public HttpStatus deleteUserWithBooks(Long userId) {
        userService.deleteUserById(userId);
        log.info("User with id {} deleted", userId);
        return HttpStatus.OK;
    }
}
