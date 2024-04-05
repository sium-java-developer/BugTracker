package org.bugtracker.services;

import org.bugtracker.entities.Bug;
import org.bugtracker.repos.BugRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BugServiceImpl implements BugService {

    private final BugRepo bugRepo;

    @Autowired
    public BugServiceImpl(BugRepo bugRepo){
        this.bugRepo = bugRepo;
    }

    // implementation of crud functionality

    @Override
    public void createBug(Bug bug) {
        bugRepo.save(bug);
    }

    @Override
    public Bug getBugById(Long id) {
        return bugRepo.findById(id).orElse(null);
    }

    @Override
    public List<Bug> getAllBugs() {
        return bugRepo.findAll();
    }

    @Override
    public void updateBug(Bug bug) {
        bugRepo.save(bug);
    }

    @Override
    public void deleteBug(Long id) {
        bugRepo.deleteById(id);
    }

    @Override
    public void saveBug(Bug bug) {
        bugRepo.save(bug);
    }
}
