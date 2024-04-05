package org.bugtracker.controllers;

import org.bugtracker.entities.User;
import org.bugtracker.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @MockBean
    private SecurityContextHolder securityContextHolder;

    // test createUser method
    @Test
    public void testCreateUser() throws Exception {
        User user = new User("testUsername", "dummyPassword", "test@email.com");

        // perform a POST request to the /users/new endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/users/new")
                .param("testUsername", user.getUserName())
                .param("dummyPassword", user.getPassword())
                .param("test@email.com", user.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/new"));

        // assert that the user is created in the database
        List<User> users = userRepo.findAll();
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals("testUsername", users.get(0).getUserName());
        Assertions.assertEquals("dummyPassword", users.get(0).getPassword());
        Assertions.assertEquals("test@email.com", users.get(0).getEmail());
    }

    // test getUserById method
    @Test
    public void testUserById() throws Exception {
        User user = userRepo.save(new User("testUsername", "dummyPassword", "test@email.com"));

        // perform a GET request to the /users/profile endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"));
    }

    // test getAllUsers method
    @Test
    public void testGetAllUsers() throws Exception {
        User user = userRepo.save(new User("testUsername1", "dummyPassword1", "test1@email.com"));
        User user2 = userRepo.save(new User("testUsername2", "dummyPassword2", "test2@email.com"));
        User user3 = userRepo.save(new User("testUsername3", "dummyPassword3", "test3@email.com"));

        // perform a GET request to the /users endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("users"));
    }

    // test updateUser method
    @Test
    public void testUpdateUser() throws Exception {
        User user = userRepo.save(new User("testUsername", "dummyPassword", "test@email.com"));

        // prepare mock request data
        String newUserName = "newUsername";
        String newPassword = "newPassword";
        String newEmail = "new@email.com";

        // prepare a mock request to the /users/{id}/edit endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/users/" + user.getId() + "/edit")
                .param("newUsername", newUserName)
                .param("newPassword", newPassword)
                .param("new@email.com", newEmail))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/" + user.getId()));

        // verify that the user was updated in the database
        User updatedUser = userRepo.findById(user.getId()).get();
        Assertions.assertEquals(newUserName, updatedUser.getUserName());
        Assertions.assertEquals(newPassword, updatedUser.getPassword());
        Assertions.assertEquals(newEmail, updatedUser.getEmail());
    }

    // test deleteUser method
    @Test
    public void testDeleteUser() throws Exception {
        User user = userRepo.save(new User("testUsername", "dummyPassword", "test@email.com"));

        // perform a DELETE request to the /users/{id}/delete endpoint
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + user.getId() + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    // test registration page
    @Test
    public void testRegistrationPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    // test registerUser method
    @Test
    public void testRegisterUser() throws Exception {
        // prepare mock user data
        String username = "testuser";
        String email = "test@email.com";
        String password = "dummyPassword";

        // perform a POST request to the /register endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .param("testuser", username)
                .param("test@email.com", email)
                .param("dummyPassword", password)
                .param("dummyPassword", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("/login"));

        // verify that the user was created in the database
        Optional<User> user = userRepo.findByUsername(username);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(email, user.get().getEmail());
    }

    // test getLoginPage
    @Test
    public void testGetLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    // test loginUser method
    public void testLoginUser() throws Exception {
        // mock a user in the database
        User user = new User("testuser", "test@email.com", "dummyPassword");
        userRepo.save(user);

        // perform a POST request to the /login endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                .param("testuser", user.getUserName())
                .param("password", user.getPassword()))
                .andExpect(status().is3xxRedirection());
    }

    // test getProfilePage
    public void testGetProfilePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("singleUser"));;
    }

    // test updateProfile
    @Test
    public void testUpdateProfile() throws Exception {
        // create a user in the database
        User user = new User("oldUsername", "oldEmailAddress@email.com", "password");
        userRepo.save(user);

        // prepare mock update data
        String newUsername = "newUsername";
        String newEmail = "newEmailAddress@email.com";

        // create a POST request to the /users/profile/update endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/users/profile/update")
                .param("username", newUsername)
                .param("email", newEmail))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/singerUser"));

        // verify that the user updated in the database
        User updatedUser = userRepo.findById(user.getId()).get();
        Assertions.assertEquals(newUsername, user.getUserName());
        Assertions.assertEquals(newEmail, user.getEmail());
    }
}