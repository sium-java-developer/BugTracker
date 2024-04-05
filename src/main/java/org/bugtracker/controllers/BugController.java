package org.bugtracker.controllers;

import org.bugtracker.entities.Bug;
import org.bugtracker.services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bugs")
public class BugController {

    private final BugService bugService;

    @Autowired
    public BugController(BugService bugService){
        this.bugService = bugService;
    }

    @PostMapping("/new")
    public String createBug(@ModelAttribute Bug bug){
        bugService.createBug(bug);
        return "redirect:/bug-create";
    }

    @GetMapping("/{id}")
    public String getBugById(@PathVariable Long id, Model model){
        Bug bug = bugService.getBugById(id);
        model.addAttribute("bug", bug);
        return "bug-detail";
    }

    @GetMapping
    public String getAllBugs(Model model){
        List<Bug> bugs = bugService.getAllBugs();
        model.addAttribute("bugs", bugs);
        return "bug-list";
    }

    @PostMapping("/{id}/edit")
    public String updateBug(@PathVariable Long id, @ModelAttribute Bug bug){
        bugService.updateBug(bug);
        return "redirect:/bug-edit";
    }

    @PostMapping("/{id}/delete")
    public String deleteBug(@PathVariable Long id){
        bugService.deleteBug(id);
        return "redirect:/bug-list";
    }

    @PostMapping("/new-bug")
    public String saveBug(@ModelAttribute Bug bug){
        bugService.saveBug(bug);
        return "redirect:/bug-list";
    }
}