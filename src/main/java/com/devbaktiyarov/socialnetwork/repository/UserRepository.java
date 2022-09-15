package com.devbaktiyarov.socialnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.devbaktiyarov.socialnetwork.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("{username: {$regex: ?0}}")
    List<User> findUsersByUsername(String username);  
    
    @Query("{$or : [{username: ?0}, {email : ?1}]}")
    Optional<User> findByUsernameOrEmail(String username, String email);

    void deleteByUsername(String username); 
}
