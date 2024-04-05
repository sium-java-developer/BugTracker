package org.bugtracker.services;

import org.bugtracker.entities.User;
import org.bugtracker.problems.NotFoundException;
import org.bugtracker.problems.UserAlreadyExistsException;

import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> getUserById(Long id);

    User getAllUsers();

    void updateUser(User user) throws NotFoundException;

    void deleteUser(Long id);

    void registerUser(User user) throws UserAlreadyExistsException;
}