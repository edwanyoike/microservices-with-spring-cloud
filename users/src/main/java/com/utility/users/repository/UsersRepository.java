package com.utility.users.repository;

import com.utility.users.data.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String Email);
}
