package com.utility.usersms.service;


import com.utility.usersms.data.UserEntity;
import com.utility.usersms.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  extends UserDetailsService {
    public UserEntity createUser(UserDto userDto);
    public UserDto getUserByEmail(String email);
}
