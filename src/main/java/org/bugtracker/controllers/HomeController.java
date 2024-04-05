package org.bugtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = {"/", "/home"})
    public String home(Model model){
        model.addAttribute("message", "Welcome to Bug Tracker Application.");
        return "home";
    }

    @GetMapping(path = "/auth")
    public String auth(){
        return "auth";
    }
}