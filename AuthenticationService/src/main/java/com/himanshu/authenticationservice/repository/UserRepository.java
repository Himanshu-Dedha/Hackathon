package com.himanshu.authenticationservice.repository;

import com.himanshu.authenticationservice.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);

}

