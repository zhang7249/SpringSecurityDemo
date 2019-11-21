package com.example.demo.dao;

import com.example.demo.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findOneByLogin(String login);

  // User findOneByLogin(String login);

}