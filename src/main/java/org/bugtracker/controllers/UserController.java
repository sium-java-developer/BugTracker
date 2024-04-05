package org.bugtracker.controllers;

import org.bugtracker.entities.User;
import org.bugtracker.problems.NotFoundException;
import org.bugtracker.problems.UserAlreadyExistsException;
import org.bugtracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    @Autowired
    public UserController(AuthenticationManagerBuilder authenticationManagerBuilder,
                          UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        Optional<User> currentUser = userService.getUserById(id);
        model.addAttribute("user", currentUser);
        return "singleUser";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "list";
    }

    @PostMapping("/{id}/edit")
    public String updateUser(@ModelAttribute User user) throws NotFoundException {
        userService.updateUser(user);
        return "redirect:/update";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/list";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Model model, User user) {
        try {
            userService.registerUser(user);
            return "redirect:/login";
        } catch (UserAlreadyExistsException ex) {
            model.addAttribute("error", "Username or email already exist!");
            return "registration";
        } catch (Exception ex) {
            model.addAttribute("error", "An error occurred during registration.");
            return "registration";
        }
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "registration";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "singleUser";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {
        try {
            Authentication authentication = (Authentication) authenticationManagerBuilder.authenticationProvider(
                    (AuthenticationProvider) new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/bug-detail";
        } catch (BadCredentialsException ex) {
            model.addAttribute("error", "Invalid Username or password.");
            return "login";
        } catch (Exception ex) {
            model.addAttribute("error", "An error occurred during authentication.");
            return "login";
        }
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user,
                                Model model,
                                Principal principal) throws NotFoundException {
        // get currently authenticated user
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);

        if (currentUser == null) {
            throw new NotFoundException("User not found.");
        }

        // update user details (excluding password)
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());

        // save the updated user
        userService.updateUser(currentUser);

        // add a success message or redirect to a profile page
        model.addAttribute("message", "Profile updates successfully.");
        return "redirect:/singleUser";
    }
}