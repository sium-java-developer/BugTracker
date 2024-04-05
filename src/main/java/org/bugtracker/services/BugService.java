package org.bugtracker.services;

import org.bugtracker.entities.Bug;

import java.util.List;

public interface BugService {

    void createBug(Bug bug);

    Bug getBugById(Long id);

    List<Bug> getAllBugs();

    void updateBug(Bug bug);

    void deleteBug(Long id);

    void saveBug(Bug bug);
}