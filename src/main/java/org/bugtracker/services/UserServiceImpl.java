package org.bugtracker.services;

import org.bugtracker.entities.User;
import org.bugtracker.problems.UserAlreadyExistsException;
import org.bugtracker.problems.NotFoundException;
import org.bugtracker.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public User getAllUsers() {
        return (User) userRepo.findAll();
    }

    @Override
    public void updateUser(User user) throws NotFoundException {
        // check if user exists
        User existingUser = userRepo.findById(user.getId()).orElseThrow(() ->
                new NotFoundException("User not found."));

        // update user details
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        // save the user
        userRepo.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }


    public void registerUser(User user) throws UserAlreadyExistsException {
        // encode password before saving
        long passwordEncoder = 1L;
        user.setPassword(String.valueOf(passwordEncoder));

        // save the user to the database
        userRepo.save(user);
    }
}