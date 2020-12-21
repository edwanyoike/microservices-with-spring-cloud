package com.utility.usersms.repository;

import com.utility.usersms.data.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String Email);
}
