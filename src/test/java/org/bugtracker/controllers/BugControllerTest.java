package org.bugtracker.controllers;

import org.bugtracker.entities.Bug;
import org.bugtracker.repos.BugRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BugControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BugRepo bugRepo;

    /**
     * Test CRUD functionality of bug controller
     */

    // test createBug method
    @Test
    public void testCreateBug() throws Exception {
        // prepare mock request data
        String title = "New Bug";
        String description = "This is a new bug";

        // perform a POST request to the /bugs/new endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/bugs/new")
                .param("title", title)
                .param("description", description))
                .andExpect((ResultMatcher) status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bugs"));

        // verify that the bug was created in the database
        List<Bug> bugs = bugRepo.findAll();
        Assertions.assertEquals(1, bugs.size());
        Assertions.assertEquals(title, bugs.get(0).getTitle());
        Assertions.assertEquals(description, bugs.get(0).getDescription());
    }

    // test getAllBugs method
    @Test
    public void testGetAllBugs() throws Exception {
        // create three bugs in the database
        bugRepo.save(new Bug("test bug 1", "this is test bug 1"));
        bugRepo.save(new Bug("test bug 2", "this is test bug2"));
        bugRepo.save(new Bug("test bug 3", "this is test bug 3"));

        // perform a GET request to the /bugs endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/bugs"))
                .andExpect(status().isOk())
                .andExpect(view().name("bugs"))
                .andExpect(model().attributeExists("bugs"));
    }

    // test getBugById() method
    @Test
    public void testGetBugById() throws Exception{
        // create a bug in the database
        Bug bug = bugRepo.save(new Bug("test bug", "description"));

        // perform a GET request to the /bugs/{id} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/bugs/" + bug.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("bug-detail"))
                .andExpect(model().attributeExists("bugs"));
    }

    // test updateBug method
    @Test
    public void testUpdateBug() throws Exception {
        // create a new bug in the database
        Bug bug = bugRepo.save(new Bug("Old Bug", "Description of old bug"));

        // prepare mock request data
        String newTitle = "Updated bug title";
        String newDescription = "Updated description for new bug";

        // perform a POST request to the /bugs/{id}/edit endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/bugs/" + bug.getId() + "/edit")
                .param("title", newTitle)
                .param("description", newDescription))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bugs/" + bug.getId()));

        // verify that the bug was updated in the database
        Bug updatedBug = bugRepo.findById(bug.getId()).get();
        Assertions.assertEquals(newTitle, updatedBug.getTitle());
        Assertions.assertEquals(newDescription, updatedBug.getDescription());
    }

    // test deleteBug() method
    @Test
    public void testDeleteBug() throws Exception {
        // create a new bug in the database
        Bug bug = bugRepo.save(new Bug("Delete Bug", "This bug is going to be deleted."));

        // perform a DELETE request to the /bugs/{id}/delete endpoint
        mockMvc.perform(MockMvcRequestBuilders.delete("/bugs/" + bug.getId() + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bugs"));

        // verify that the bug was deleted from the database
        Assertions.assertFalse(bugRepo.existsById(bug.getId()));
    }
}