package org.bugtracker.repos;

import org.bugtracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select o from User o where o.userName=:un")
    Optional<User> findByUsername(@Param("un") String username);

    @Query("select o from User o where o.firstName=:fn")
    Iterable<User> findByFirstName(@Param("fn") String firstName);

    @Query("select o from User o where o.lastName=:ln")
    Iterable<User> findByLastName(@Param("ln") String lastName);

    @Query("select o from User o where o.email=:email")
    Iterable<User> findByEmail(@Param("email") String email);

    @Query("select o from User o where o.id=:id")
    Iterable<User> findByAssignedBugsId(@Param("id") Long bugId);

}