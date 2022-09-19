package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;

public interface UserService {
    User createUser(UserDto userDto);

    User updateUser(Long id,UserDto userDto);

    User getUserById(Long id);

    void deleteUserById(Long id);
}
