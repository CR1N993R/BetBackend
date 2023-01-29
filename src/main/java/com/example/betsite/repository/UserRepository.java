package com.example.betsite.repository;

import com.example.betsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User getUserByUsername(String username);
    User getUserById(String id);
}
