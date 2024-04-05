package org.bugtracker.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "assignedUsers")
    private Set<Bug> assignedBugs = new HashSet<>();

    public User(String username, String email, String password) {
        this.userName = username;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    // create getters and setters for id
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    // create getter and setter for userName
    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    // create getter and setter for password
    public String getPassword(){
        return password.getBytes();
    }

    public void setPassword(String password){
        this.password = password;
    }

    // create getter and setter for firstName
    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    // create getter and setter for lastName
    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    // create getter and setter for email
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    // create getter and setter for assignedBugs
    public Set<Bug> getAssignedBugs(){
        return assignedBugs;
    }

    public void setAssignedBugs(Set<Bug> assignedBugs){
        this.assignedBugs = assignedBugs;
    }
}