package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.StorageImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private long id;

    private StorageImpl storage;

    public UserServiceImpl(StorageImpl storage) {
        this.storage = storage;
    }

    @Override
    public User createUser(UserDto userDto) {
        // сгенерировать идентификатор
        // создать пользователя
        // вернуть сохраненного пользователя со всеми необходимыми полями id
        User user = new User();
        user.setId(++id);
        user.setAge(userDto.getAge());
        user.setFullName(userDto.getFullName());
        user.setTitle(userDto.getTitle());
        storage.addUserToStorage(user);
        return user;
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        User user = storage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id = " + id + " не найден!");
        }
        user.setAge(userDto.getAge());
        user.setFullName(userDto.getFullName());
        user.setTitle(userDto.getTitle());
        storage.updateUser(id, user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return storage.getUserById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = storage.getUserById(id);
        if (user != null) {
            storage.deleteUser(id);
        } else {
            throw new NotFoundException("Пользователь с id = " + id + " не найден!");
        }
    }
}
