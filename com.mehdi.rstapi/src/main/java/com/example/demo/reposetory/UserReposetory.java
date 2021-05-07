package com.example.demo.reposetory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.UserEntity;
@Repository
public interface UserReposetory extends CrudRepository<UserEntity, Long> {
 UserEntity findByEmail(String email);
}
