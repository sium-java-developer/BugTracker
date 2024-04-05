package org.bugtracker.services;

import org.bugtracker.entities.User;
import org.bugtracker.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepo userRepo;

    // create method createUserInDatabase
    private User createUserInDatabase(String userName){
        User user = new User();
        user.setUserName(userName);
        user.setEmail(userName + "@email.com");
        user.setPassword("password");
        return userRepo.save(user);
    }

    // test createUser method
    @Test
    public void testCreateUser() {
        // create user in the database
        User user = new User();
        user.setUserName("testUser1");
        user.setEmail("test1@email.com");
        user.setPassword("password");

        // save it to the database
        userRepo.save(user);

        // assert that the user was saved successfully
        Assertions.assertNotNull(user.getId());
    }

    // test getUserById method
    @Test
    public void testGetUserById(){
        // create a user in the database
        User user = createUserInDatabase("testUser2");

        // retrieved the user from the database
        Optional<User> retrievedUser = userRepo.findById(user.getId());

        // assert that the retrieved user matches the expected values
        Assertions.assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals(user.getUserName(), retrievedUser.get().getUserName());
        Assertions.assertEquals(user.getEmail(), retrievedUser.get().getEmail());
    }

    // test getAllUsers method
    @Test
    public void testGetAllUsers(){
        // create the first user in the database
        User user1 = new User();
        user1.setUserName("testUser1");
        user1.setEmail("test1@email.com");
        user1.setPassword("password111");
        userRepo.save(user1);

        // create the second user in the database
        User user2 = new User();
        user2.setUserName("testUser2");
        user2.setEmail("test2@email.com");
        user2.setPassword("password222");
        userRepo.save(user2);

        // create the third user in the database
        User user3 = new User();
        user3.setUserName("testUser3");
        user3.setEmail("test3@email.com");
        user3.setPassword("password333");
        userRepo.save(user3);

        // retrieved all users from the database
        List<User> retrievedUsers = userRepo.findAll();

        // assert that the retrieved user matches the expected number of users
        Assertions.assertEquals(3, retrievedUsers.size());
        Assertions.assertTrue(retrievedUsers.contains(user1));
        Assertions.assertTrue(retrievedUsers.contains(user2));
        Assertions.assertTrue(retrievedUsers.contains(user3));
    }

    // test updateUser method
    @Test
    public void testUpdateUser(){
        // create a user in the database
        User user = createUserInDatabase("testUser11");

        // update the user in the database
        user.setEmail("test11@email.com");

        // save the user in the database
        userRepo.save(user);

        // retrieve the user from the database
        Optional<User> retrievedUser = userRepo.findById(user.getId());

        // assert that the user is updated properly
        Assertions.assertTrue(retrievedUser.isPresent());
        Assertions.assertEquals("test11@email.com", retrievedUser.get().getEmail());
    }

    // test deleteUser method
    @Test
    public void testDeleteUser(){
        // create a user in the database
        User user = createUserInDatabase("testUser12");

        // delete the user from the database
        userRepo.deleteById(user.getId());

        // assert that the user was deleted
        Optional<User> retrievedUser = userRepo.findById(user.getId());
        Assertions.assertFalse(retrievedUser.isPresent());
    }
}