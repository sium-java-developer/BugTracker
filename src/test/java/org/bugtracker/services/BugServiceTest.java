package org.bugtracker.services;

import org.bugtracker.entities.Bug;
import org.bugtracker.repos.BugRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BugServiceTest {

    @Autowired
    private BugRepo bugRepo;

    // create a bug in database
    private Bug createBugInDatabase(){
        // create a new bug and save it to the database
        Bug bug = new Bug();
        bug.setTitle("This is a temporary bug");
        bug.setDescription("This is a temporary bug");
        bug.setPriority(Bug.Priority.HIGH);
        bug.setStatus(Bug.Status.OPEN);
        return bugRepo.save(bug);
    }

    // test createBug method
    @Test
    public void testCreateBug(){
        // create a new bug object
        Bug bug = new Bug();
        bug.setTitle("This is a title for testCreateBug object.");
        bug.setDescription("This is a description for testCreateBug object.");
        bug.setPriority(Bug.Priority.LOW);
        bug.setStatus(Bug.Status.OPEN);

        // save this bug to the database
        bugRepo.save(bug);

        // assert that the bug was created successfully
        Assertions.assertNotNull(bug.getId());
    }

    // test getBugById method
    @Test
    public void testGetBugById(){
        // create a bug in the database
        Bug bug = createBugInDatabase();

        // retrieve a bug from the database
        Optional<Bug> retrievedBug = bugRepo.findById(bug.getId());

        // assert that the retrieved bug matches the expected value
        Assertions.assertTrue(retrievedBug.isPresent());
        Assertions.assertEquals(bug.getTitle(), retrievedBug.get().getTitle());
        Assertions.assertEquals(bug.getDescription(), retrievedBug.get().getDescription());
        Assertions.assertEquals(bug.getPriority(), retrievedBug.get().getPriority());
        Assertions.assertEquals(bug.getStatus(), retrievedBug.get().getStatus());
    }

    // test getAllBugs method
    @Test
    public void testGetAllBugs(){
        // create three new bugs in the database

        // create first bug
        Bug bug1 = new Bug();
        bug1.setTitle("This is bug1");
        bug1.setDescription("This is description for bug1 object.");
        bug1.setPriority(Bug.Priority.LOW);
        bug1.setStatus(Bug.Status.OPEN);
        bugRepo.save(bug1);

        // create second bug
        Bug bug2 = new Bug();
        bug2.setTitle("This is bug2");
        bug2.setDescription("This is description for bug2 object.");
        bug2.setPriority(Bug.Priority.MEDIUM);
        bug2.setStatus(Bug.Status.OPEN);
        bugRepo.save(bug2);

        // create third bug
        Bug bug3 = new Bug();
        bug3.setTitle("This is bug3");
        bug3.setDescription("This is description for bug3 object.");
        bug3.setPriority(Bug.Priority.HIGH);
        bug3.setStatus(Bug.Status.OPEN);
        bugRepo.save(bug3);

        // retrieve all bugs from the database
        List<Bug> allBugs = bugRepo.findAll();

        // assert that retrieved bug matched the expected number of bugs
        Assertions.assertEquals(allBugs.size(), 3);
        Assertions.assertTrue(allBugs.contains(bug1));
        Assertions.assertTrue(allBugs.contains(bug2));
        Assertions.assertTrue(allBugs.contains(bug3));
    }

    // test updateBug method
    @Test
    public void testUpdateBug(){
        // create a bug in database
        Bug bug = createBugInDatabase();

        // update the bug
        bug.setTitle("This bug is going to be updated.");
        bug.setStatus(Bug.Status.IN_PROGRESS);

        // save the bug in the database
        bugRepo.save(bug);

        // retrieve the bug from the database
        Optional<Bug> updatedBug = bugRepo.findById(bug.getId());

        // assert that the bug was updated successfully
        Assertions.assertTrue(updatedBug.isPresent());
        Assertions.assertEquals("This bug is going to be updated.", updatedBug.get().getTitle());
        Assertions.assertEquals(Bug.Status.IN_PROGRESS, updatedBug.get().getStatus());
    }

    // test deleteBug method
    @Test
    public void testDeleteBug(){
        // create a bug in the database
        Bug bug = createBugInDatabase();

        // delete the bug from the database
        bugRepo.delete(bug);

        // assert that the bug was deleted successfully from the database
        Optional<Bug> deletedBug = bugRepo.findById(bug.getId());
        Assertions.assertFalse(deletedBug.isPresent());
    }
}