package com.utility.users.service;

import com.utility.users.data.UserEntity;
import com.utility.users.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  extends UserDetailsService {
    public UserEntity createUser(UserDto userDto);
    public UserDto getUserByEmail(String email);
}
