package ru.otus.example.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.example.models.User;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    public Optional<User> findUserByUsername(String username);
}
